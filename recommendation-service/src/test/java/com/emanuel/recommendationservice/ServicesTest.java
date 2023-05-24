package com.emanuel.recommendationservice;

import com.emanuel.recommendationservice.services.*;
import com.emanuel.starterlibrary.dtos.AudioDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ServicesTest {
    @Mock
    private RecommendationService recommendationService;
    private AudioRecommendationService audioRecommendationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        audioRecommendationService = new AudioRecommendationService(recommendationService);
    }

    @Test
    void testGetRandomRecommendedAudio() {
        // assume
        AudioDto expectedAudio = new AudioDto();
        when(recommendationService.getRandomRecommendedMedia(AudioDto.class, "audio")).thenReturn(expectedAudio);

        // act
        AudioDto actualAudio = audioRecommendationService.getRandomRecommendedAudio();

        // assert
        assertEquals(expectedAudio, actualAudio);
    }
}
