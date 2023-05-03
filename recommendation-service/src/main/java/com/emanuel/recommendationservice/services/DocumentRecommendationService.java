package com.emanuel.recommendationservice.services;

import com.emanuel.starterlibrary.dtos.DocumentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DocumentRecommendationService {
    private static final double RATING_WEIGHT = 0.50;
    private static final double EXTENSION_WEIGHT = 0.25;
    private static final double NR_OF_PAGES_WEIGHT = 0.25;
    private final RecommendationService recommendationService;

    public List<DocumentDto> getRecommendedDocument(@NotNull @Min(value = 1) int numberOfDocuments) {
        List<DocumentDto> documents = recommendationService.getDtoListFromDatabase(DocumentDto.class, "document");
        if (documents.size() < numberOfDocuments){
            throw new ArithmeticException("The database has less number of documents than you are trying to retrieve");
        }
        Map<Long, Double> scores = getDocumentScores(documents);
        return recommendationService.getSortedMedia(numberOfDocuments, documents, scores);
    }

    private Map<Long, Double> getDocumentScores(List<DocumentDto> documents) {
        return documents.stream().collect(Collectors.toMap(DocumentDto::getId, documentDto -> {
            Float generalRating = 0f;
            double extensionScore = 0;
            double numberOfPagesScore = 0;
            // get rating score
            generalRating = recommendationService.getRating(documentDto, generalRating);
            // calculate extension score
            String extension = Optional.ofNullable(documentDto.getExtension()).orElse("");
            if (extension.equalsIgnoreCase("pdf")) {
                extensionScore = 10;
            } else if (extension.equalsIgnoreCase("docx")){
                extensionScore = 5;
            }
            // calculate number of pages score
            int numberOfPages = Optional.ofNullable(documentDto.getNumberOfPages()).orElse(0);
            if (numberOfPages >= 1 && numberOfPages <= 20){
                numberOfPagesScore = 10;
            } else if (numberOfPages > 20 && numberOfPages <= 200) {
                numberOfPagesScore = 8;
            } else if (numberOfPages > 200 && numberOfPages <= 500) {
                numberOfPagesScore = 5;
            }
            return generalRating * RATING_WEIGHT + extensionScore * EXTENSION_WEIGHT + numberOfPagesScore * NR_OF_PAGES_WEIGHT;
        }));
    }

    public DocumentDto getRandomRecommendedDocument() {
        return recommendationService.getRandomRecommendedMedia(DocumentDto.class, "document");
    }
}
