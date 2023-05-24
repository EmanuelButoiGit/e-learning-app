package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.controllers.AudioRecommendationController;
import com.emanuel.recommendationservice.services.AudioRecommendationService;
import com.emanuel.starterlibrary.dtos.AudioDto;
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

class AudioRecommendationControllerTest {
    @Mock
    private AudioRecommendationService audioRecommendationService;

    private AudioRecommendationController audioRecommendationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        audioRecommendationController = new AudioRecommendationController(audioRecommendationService);
    }

    @Test
    void testGetRecommendedAudio() {
        // assume
        int numberOfAudios = 5;
        List<AudioDto> expectedAudios = Arrays.asList(new AudioDto(), new AudioDto(), new AudioDto());
        when(audioRecommendationService.getRecommendedAudio(anyInt())).thenReturn(expectedAudios);

        // act
        List<AudioDto> actualAudios = audioRecommendationController.getRecommendedAudio(numberOfAudios);

        // assert
        assertEquals(expectedAudios, actualAudios);
    }

    @Test
    void testGetRandomRecommendedAudio() {
        // assume
        AudioDto expectedAudio = new AudioDto();
        when(audioRecommendationService.getRandomRecommendedAudio()).thenReturn(expectedAudio);

        // act
        AudioDto actualAudio = audioRecommendationController.getRandomRecommendedAudio();

        // assert
        assertEquals(expectedAudio, actualAudio);
    }
}
