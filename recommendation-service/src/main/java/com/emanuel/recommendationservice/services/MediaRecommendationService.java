package com.emanuel.recommendationservice.services;

import com.emanuel.starterlibrary.dtos.MediaDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MediaRecommendationService {
    private final RecommendationService recommendationService;
    private static final String MEDIA = "media";

    public List<MediaDto> getRecommendedMedia(int numberOfMedias) {
        return recommendationService.getMediasBasedOnRating(MediaDto.class, MEDIA, numberOfMedias);
    }

    public MediaDto getRandomRecommendedMedia() {
        return recommendationService.getRandomRecommendedMedia(MediaDto.class, MEDIA);
    }

    public List<String> getTopMedia(int numberOfMedias) {
        return recommendationService.getTitlesOfMediasBasedOnRating(MediaDto.class, MEDIA, numberOfMedias);
    }
}
