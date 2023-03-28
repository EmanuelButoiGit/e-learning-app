package com.emanuel.recommendationservice.services;

import com.emanuel.recommendationservice.dtos.ImageDto;
import com.emanuel.recommendationservice.dtos.VideoDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VideoRecommendationService {
    private final RecommendationService recommendationService;

    public List<VideoDto> getRecommendedVideo(int numberOfVideos) {
        List<VideoDto> videos = recommendationService.getDtoListFromDatabase(VideoDto.class, "video");
        if (videos.size() < numberOfVideos){
            throw new ArithmeticException("The database has less number of videos than you are trying to retrieve");
        }
        // set weights for each criterion
        double ratingWeight = 0.50;
        double qualityWeight = 0.15;
        double durationWeight = 0.10;
        double aspectRatioWeight = 0.10;
        double fpsWeight = 0.15;
        Map<Long, Double> scores = getImageScores(videos, ratingWeight, qualityWeight, durationWeight, aspectRatioWeight, fpsWeight);
        return recommendationService.getSortedMedia(numberOfVideos, videos, scores);
    }

    private Map<Long, Double> getImageScores(List<VideoDto> videos, double ratingWeight, double qualityWeight, double durationWeight, double aspectRatioWeight, double fpsWeight) {
        return videos.stream().collect(Collectors.toMap(ImageDto::getId, videoDto -> {
            Float generalRating = 0f;
            int durationScore = 0;
            int aspectRatioScore = 0;
            // get rating score
            generalRating = recommendationService.getRating(videoDto, generalRating);
            // calculate quality score
            int resolutionQuality = Optional.ofNullable(videoDto.getResolutionQuality()).orElse(0);
            int resolutionQualityScore = recommendationService.calculateResolutionQuality(resolutionQuality);
            // calculate duration score
            long duration = Optional.ofNullable(videoDto.getDuration()).orElse(0L);
            if (duration > 600 && duration < 1200){
                durationScore = 10;
            }
            // calculate aspect ratio score
            double aspectRatio = Optional.ofNullable(videoDto.getAspectRatio()).orElse(0.0);
            if (aspectRatio > 1){
                aspectRatioScore = 10;
            }
            // calculate FPS score
            double fps = Optional.ofNullable(videoDto.getFps()).orElse(0.0);
            int fpsScore = getFpsScore(fps);
            return generalRating * ratingWeight + resolutionQualityScore * qualityWeight + durationScore * durationWeight + aspectRatioScore * aspectRatioWeight + fpsScore * fpsWeight;
        }));
    }

    private int getFpsScore(double fps){
        if (fps <= 15) {
            return 2;
        } else if (fps > 15 && fps < 30) {
            return 4;
        } else if (fps >= 30 && fps < 60) {
            return 6;
        } else if (fps >= 60 && fps < 120) {
            return 8;
        } else if (fps >= 120) {
            return 10;
        }
        return 0;
    }

    public VideoDto getRandomRecommendedVideo() {
        return recommendationService.getRandomRecommendedMedia(VideoDto.class, "video");
    }
}
