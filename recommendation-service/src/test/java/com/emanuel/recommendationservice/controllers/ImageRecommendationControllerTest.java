package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.controllers.ImageRecommendationController;
import com.emanuel.recommendationservice.services.ImageRecommendationService;
import com.emanuel.starterlibrary.dtos.ImageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class ImageRecommendationControllerTest {
    @Mock
    private ImageRecommendationService imageRecommendationService;

    private ImageRecommendationController imageRecommendationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        imageRecommendationController = new ImageRecommendationController(imageRecommendationService);
    }

    @Test
    void testGetRecommendedImage() {
        // assume
        int numberOfImages = 5;
        List<ImageDto> expectedImages = Arrays.asList(new ImageDto(), new ImageDto(), new ImageDto());
        when(imageRecommendationService.getRecommendedImage(anyInt())).thenReturn(expectedImages);

        // act
        List<ImageDto> actualImages = imageRecommendationController.getRecommendedImage(numberOfImages);

        // assert
        assertEquals(expectedImages, actualImages);
    }

    @Test
    void testGetRandomRecommendedImage() {
        // assume
        ImageDto expectedImage = new ImageDto();
        when(imageRecommendationService.getRandomRecommendedImage()).thenReturn(expectedImage);

        // act
        ImageDto actualImage = imageRecommendationController.getRandomRecommendedImage();

        // assert
        assertEquals(expectedImage, actualImage);
    }
}
