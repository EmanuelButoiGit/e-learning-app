package com.emanuel.recommendationservice.services;

import com.emanuel.starterlibrary.dtos.AudioDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AudioRecommendationService {
    private static final double DURATION_WEIGHT = 0.15;
    private static final double RATING_WEIGHT = 0.55;
    private static final double EXTENSION_WEIGHT = 0.15;
    private static final double SAMPLE_RATE_WEIGHT = 0.15;
    private final RecommendationService recommendationService;

    public List<AudioDto> getRecommendedAudio(@NotNull @Min(value = 1) int numberOfAudios) {
        List<AudioDto> audios = recommendationService.getDtoListFromDatabase(AudioDto.class, "audio");
        if (audios.size() < numberOfAudios){
            throw new ArithmeticException("The database has less number of audios than you are trying to retrieve");
        }
        Map<Long, Double> scores = getAudioScores(audios);
        return recommendationService.getSortedMedia(numberOfAudios, audios, scores);
    }

    private Map<Long, Double> getAudioScores(List<AudioDto> audios) {
        return audios.stream().collect(Collectors.toMap(AudioDto::getId, audioDto -> {
            Float generalRating = 0f;
            double durationScore = 0;
            double extensionScore = 0;
            double sampleScore = 0;
            // get rating score
            generalRating = recommendationService.getRating(audioDto, generalRating);
            // calculate duration score
            long duration = Optional.ofNullable(audioDto.getDuration()).orElse(0L);
            if (duration > 600 && duration < 1200){
                durationScore = 10;
            }
            // calculate extension score
            String extension = Optional.ofNullable(audioDto.getExtension()).orElse("");
            if (extension.equalsIgnoreCase("wav")) {
                extensionScore = 10;
            }
            // get sample score
            Float sampleRate = Optional.ofNullable(audioDto.getSampleRate()).orElse(0F);
            sampleScore = calculateSampleScore(sampleRate, sampleScore);
            return generalRating * RATING_WEIGHT + extensionScore * EXTENSION_WEIGHT + sampleScore * SAMPLE_RATE_WEIGHT + durationScore * DURATION_WEIGHT;
        }));
    }

    private double calculateSampleScore(Float sampleRate, double sampleScore) {
        if (sampleRate >= 44100 && sampleRate < 48000) {
            sampleScore = 8;
        } else if (sampleRate >= 48000 && sampleRate < 96000) {
            sampleScore = 10;
        } else if (sampleRate >= 96000 && sampleRate < 192000) {
            sampleScore = 8;
        } else if (sampleRate >= 192000) {
            sampleScore = 6;
        } else if (sampleRate < 44100 && sampleRate >= 22050) {
            sampleScore = 4;
        } else if (sampleRate < 22050 && sampleRate >= 11025) {
            sampleScore = 2;
        } else if (sampleRate < 11025) {
            sampleScore = 1;
        }
        return sampleScore;
    }

    public AudioDto getRandomRecommendedAudio() {
        return recommendationService.getRandomRecommendedMedia(AudioDto.class, "audio");
    }
}
