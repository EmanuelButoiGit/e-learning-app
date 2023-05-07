package com.emanuel.recommendationservice.services;

import com.emanuel.starterlibrary.dtos.ImageDto;
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
public class ImageRecommendationService {
    private static final double RATING_WEIGHT = 0.50;
    private static final double QUALITY_WEIGHT = 0.50;
    private final RecommendationService recommendationService;

    public List<ImageDto> getRecommendedImage(@NotNull @Min(value = 1) int numberOfImages) {
        List<ImageDto> images = recommendationService.getDtoListFromDatabase(ImageDto.class, "image");
        if (images.size() < numberOfImages){
            throw new ArithmeticException("The database has less number of images than you are trying to retrieve");
        }
        Map<Long, Double> scores = getImageScores(images);
        return recommendationService.getSortedMedia(numberOfImages, images, scores);
    }

    private Map<Long, Double> getImageScores(List<ImageDto> images) {
        return images.stream().collect(Collectors.toMap(ImageDto::getId, imageDto -> {
            // get rating score
            Float generalRating = recommendationService.getRating(imageDto);
            // calculate quality score
            int resolutionQuality = Optional.ofNullable(imageDto.getResolutionQuality()).orElse(0);
            int resolutionQualityScore = recommendationService.calculateResolutionQuality(resolutionQuality);
            return generalRating * RATING_WEIGHT + resolutionQualityScore * QUALITY_WEIGHT;
        }));
    }

    public ImageDto getRandomRecommendedImage() {
        return recommendationService.getRandomRecommendedMedia(ImageDto.class, "image");
    }
}
