package com.emanuel.recommendationservice.services;

import com.emanuel.starterlibrary.dtos.ImageDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageRecommendationService {
    private final RecommendationService recommendationService;

    public List<ImageDto> getRecommendedImage(int numberOfImages) {
        List<ImageDto> images = recommendationService.getDtoListFromDatabase(ImageDto.class, "image");
        if (images.size() < numberOfImages){
            throw new ArithmeticException("The database has less number of images than you are trying to retrieve");
        }
        // set weights for each criterion
        double ratingWeight = 0.50;
        double qualityWeight = 0.50;
        Map<Long, Double> scores = getImageScores(images, ratingWeight, qualityWeight);
        return recommendationService.getSortedMedia(numberOfImages, images, scores);
    }

    private Map<Long, Double> getImageScores(List<ImageDto> images, double ratingWeight, double qualityWeight) {
        return images.stream().collect(Collectors.toMap(ImageDto::getId, imageDto -> {
            // get rating score
            Float generalRating = 0f;
            generalRating = recommendationService.getRating(imageDto, generalRating);
            // calculate quality score
            int resolutionQuality = Optional.ofNullable(imageDto.getResolutionQuality()).orElse(0);
            int resolutionQualityScore = recommendationService.calculateResolutionQuality(resolutionQuality);
            return generalRating * ratingWeight + resolutionQualityScore * qualityWeight;
        }));
    }

    public ImageDto getRandomRecommendedImage() {
        return recommendationService.getRandomRecommendedMedia(ImageDto.class, "image");
    }
}
