package com.emanuel.recommendationservice.services;

import com.emanuel.recommendationservice.dtos.AudioDto;
import com.emanuel.recommendationservice.dtos.DocumentDto;
import com.emanuel.recommendationservice.dtos.MediaDto;
import com.emanuel.recommendationservice.dtos.RatingDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DocumentRecommendationService {
    private final RecommendationService recommendationService;

    public List<DocumentDto> getRecommendedDocument(int numberOfDocuments) {
        List<DocumentDto> documents = recommendationService.getDtoListFromDatabase(DocumentDto.class);
        if (documents.size() < numberOfDocuments){
            throw new ArithmeticException("The database has less number of documents than you are trying to retrieve");
        }
        // set weights for each criterion
        double ratingWeight = 0.50;
        double extensionWeight = 0.25;
        double numberOfPagesWeight = 0.25;
        Map<Long, Double> scores = getDocumentScores(documents, ratingWeight, extensionWeight, numberOfPagesWeight);
        return recommendationService.getSortedMedia(numberOfDocuments, documents, scores);
    }

    private Map<Long, Double> getDocumentScores(List<DocumentDto> documents, double ratingWeight, double extensionWeight, double numberOfPagesWeight) {
        return documents.stream().collect(Collectors.toMap(DocumentDto::getId, documentDto -> {
            Float generalRating = 0f;
            double extensionScore = 0;
            double numberOfPagesScore = 0;
            // get rating score
            generalRating = recommendationService.getRating(documentDto, generalRating);
            // calculate extension score
            String fileName = Optional.ofNullable(documentDto.getFileName()).orElse("");
            String[] parts = fileName.split("\\.");
            String extension = parts[parts.length - 1];
            if (extension.equalsIgnoreCase("pdf")) {
                extensionScore = 10;
            } else if (extension.equalsIgnoreCase("docx")){
                extensionScore = 5;
            }
            // calculate number of pages score
            int numberOfPages = Optional.ofNullable(documentDto.getNumberOfPages()).orElse(0);
            if (numberOfPages >= 5 && numberOfPages <= 20){
                numberOfPagesScore = 10;
            } else if (numberOfPages > 20 && numberOfPages <= 200) {
                numberOfPagesScore = 8;
            } else if (numberOfPages > 200 && numberOfPages <= 500) {
                numberOfPagesScore = 5;
            }
            return generalRating * ratingWeight + extensionScore * extensionWeight + numberOfPagesScore * numberOfPagesWeight;
        }));
    }

    public DocumentDto getRandomRecommendedDocument() {
        return recommendationService.getRandomRecommendedMedia(DocumentDto.class);
    }
}
