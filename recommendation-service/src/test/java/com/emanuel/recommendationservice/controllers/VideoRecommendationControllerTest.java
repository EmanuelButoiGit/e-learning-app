package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.controllers.VideoRecommendationController;
import com.emanuel.recommendationservice.services.VideoRecommendationService;
import com.emanuel.starterlibrary.dtos.VideoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class VideoRecommendationControllerTest {
    @Mock
    private VideoRecommendationService videoRecommendationService;

    private VideoRecommendationController videoRecommendationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        videoRecommendationController = new VideoRecommendationController(videoRecommendationService);
    }

    @Test
    void testGetRecommendedVideo() {
        // assume
        int numberOfVideos = 5;
        List<VideoDto> expectedVideos = Arrays.asList(new VideoDto(), new VideoDto(), new VideoDto());
        when(videoRecommendationService.getRecommendedVideo(anyInt())).thenReturn(expectedVideos);

        // act
        List<VideoDto> actualVideos = videoRecommendationController.getRecommendedVideo(numberOfVideos);

        // assert
        assertEquals(expectedVideos, actualVideos);
    }

    @Test
    void testGetRandomRecommendedVideo() {
        // assume
        VideoDto expectedVideo = new VideoDto();
        when(videoRecommendationService.getRandomRecommendedVideo()).thenReturn(expectedVideo);

        // act
        VideoDto actualVideo = videoRecommendationController.getRandomRecommendedVideo();

        // assert
        assertEquals(expectedVideo, actualVideo);
    }
}
