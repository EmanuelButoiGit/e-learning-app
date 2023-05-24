package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.services.MediaRecommendationService;
import com.emanuel.starterlibrary.dtos.MediaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class MediaRecommendationControllerTest {
    @Mock
    private MediaRecommendationService mediaRecommendationService;

    private MediaRecommendationController mediaRecommendationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mediaRecommendationController = new MediaRecommendationController(mediaRecommendationService);
    }

    @Test
    void testGetRecommendedMedia() {
        // assume
        int numberOfMedias = 5;
        List<MediaDto> expectedMedias = Arrays.asList(new MediaDto(), new MediaDto(), new MediaDto());
        when(mediaRecommendationService.getRecommendedMedia(anyInt())).thenReturn(expectedMedias);

        // act
        List<MediaDto> actualMedias = mediaRecommendationController.getRecommendedMedia(numberOfMedias);

        // assert
        assertEquals(expectedMedias, actualMedias);
    }

    @Test
    void testGetRandomRecommendedMedia() {
        // assume
        MediaDto expectedMedia = new MediaDto();
        when(mediaRecommendationService.getRandomRecommendedMedia()).thenReturn(expectedMedia);

        // act
        MediaDto actualMedia = mediaRecommendationController.getRandomRecommendedMedia();

        // assert
        assertEquals(expectedMedia, actualMedia);
    }

    @Test
    void testGetTopMedia() {
        // assume
        int numberOfMedias = 3;
        List<String> expectedTopMedia = Arrays.asList("Media1", "Media2", "Media3");
        when(mediaRecommendationService.getTopMedia(anyInt())).thenReturn(expectedTopMedia);

        // act
        List<String> actualTopMedia = mediaRecommendationController.getTopMedia(numberOfMedias);

        // assert
        assertEquals(expectedTopMedia, actualTopMedia);
    }
}
