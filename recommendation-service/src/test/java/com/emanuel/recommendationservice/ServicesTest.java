package com.emanuel.recommendationservice;

import com.emanuel.recommendationservice.services.*;
import com.emanuel.starterlibrary.dtos.AudioDto;
import com.emanuel.starterlibrary.dtos.DocumentDto;
import com.emanuel.starterlibrary.dtos.ImageDto;
import com.emanuel.starterlibrary.dtos.VideoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ServicesTest {
    @Mock
    private RecommendationService recommendationService;

    private AudioRecommendationService audioRecommendationService;
    private DocumentRecommendationService documentRecommendationService;
    private ImageRecommendationService imageRecommendationService;
    private VideoRecommendationService videoRecommendationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        audioRecommendationService = new AudioRecommendationService(recommendationService);
        documentRecommendationService = new DocumentRecommendationService(recommendationService);
        imageRecommendationService = new ImageRecommendationService(recommendationService);
        videoRecommendationService = new VideoRecommendationService(recommendationService);
    }

    @Test
    void testGetRandomRecommended() {
        // Audio
        AudioDto expectedAudio = new AudioDto();
        when(recommendationService.getRandomRecommendedMedia(AudioDto.class, "audio")).thenReturn(expectedAudio);
        AudioDto actualAudio = audioRecommendationService.getRandomRecommendedAudio();
        assertEquals(expectedAudio, actualAudio);

        // Document
        DocumentDto expectedDocument = new DocumentDto();
        when(recommendationService.getRandomRecommendedMedia(DocumentDto.class, "document")).thenReturn(expectedDocument);
        DocumentDto actualDocument = documentRecommendationService.getRandomRecommendedDocument();
        assertEquals(expectedDocument, actualDocument);

        // Image
        ImageDto expectedImage = new ImageDto();
        when(recommendationService.getRandomRecommendedMedia(ImageDto.class, "image")).thenReturn(expectedImage);
        ImageDto actualImage = imageRecommendationService.getRandomRecommendedImage();
        assertEquals(expectedImage, actualImage);

        // Video
        VideoDto expectedVideo = new VideoDto();
        when(recommendationService.getRandomRecommendedMedia(VideoDto.class, "video")).thenReturn(expectedVideo);
        VideoDto actualVideo = videoRecommendationService.getRandomRecommendedVideo();
        assertEquals(expectedVideo, actualVideo);
    }
}
