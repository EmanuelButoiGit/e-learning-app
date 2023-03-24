package com.emanuel.recommendationservice.services;

import com.emanuel.recommendationservice.dtos.AudioDto;
import com.emanuel.recommendationservice.dtos.DocumentDto;
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

    public DocumentDto getRandomRecommendedDocument() {
        return recommendationService.getRandomRecommendedMedia(DocumentDto.class);
    }
}
