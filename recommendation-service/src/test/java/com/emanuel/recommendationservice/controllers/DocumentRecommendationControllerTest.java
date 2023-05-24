package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.controllers.DocumentRecommendationController;
import com.emanuel.recommendationservice.services.DocumentRecommendationService;
import com.emanuel.starterlibrary.dtos.DocumentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class DocumentRecommendationControllerTest {
    @Mock
    private DocumentRecommendationService documentRecommendationService;

    private DocumentRecommendationController documentRecommendationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        documentRecommendationController = new DocumentRecommendationController(documentRecommendationService);
    }

    @Test
    void testGetRecommendedDocument() {
        // assume
        int numberOfDocuments = 5;
        List<DocumentDto> expectedDocuments = Arrays.asList(new DocumentDto(), new DocumentDto(), new DocumentDto());
        when(documentRecommendationService.getRecommendedDocument(anyInt())).thenReturn(expectedDocuments);

        // act
        List<DocumentDto> actualDocuments = documentRecommendationController.getRecommendedDocument(numberOfDocuments);

        // assert
        assertEquals(expectedDocuments, actualDocuments);
    }

    @Test
    void testGetRandomRecommendedDocument() {
        // assume
        DocumentDto expectedDocument = new DocumentDto();
        when(documentRecommendationService.getRandomRecommendedDocument()).thenReturn(expectedDocument);

        // act
        DocumentDto actualDocument = documentRecommendationController.getRandomRecommendedAudio();

        // assert
        assertEquals(expectedDocument, actualDocument);
    }
}
