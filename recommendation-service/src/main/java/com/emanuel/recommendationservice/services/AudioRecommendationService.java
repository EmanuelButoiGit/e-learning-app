package com.emanuel.recommendationservice.services;

import com.emanuel.recommendationservice.dtos.AudioDto;
import com.emanuel.recommendationservice.dtos.RatingDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AudioRecommendationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioRecommendationService.class);
    private final RecommendationService recommendationService;

    public List<AudioDto> getRecommendedAudio(int nrOfAudios) {
        List<AudioDto> audios = recommendationService.getDtoListFromDatabase(AudioDto.class);
        if (audios.size() < nrOfAudios){
            throw new ArithmeticException("The database has less number of audios than you are trying to retrieve");
        }
        // set weights for each criterion
        double durationWeight = 0.15;
        double ratingWeight = 0.55;
        double extensionWeight = 0.15;
        double sampleRateWeight = 0.15;
        Map<Long, Double> scores = getAudioScores(audios, durationWeight, ratingWeight, extensionWeight, sampleRateWeight);

        List<Map.Entry<Long, Double>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        List<Long> audioIds = sortedScores.stream()
                .limit(nrOfAudios)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<AudioDto> sortedAudios = new ArrayList<>();
        for (Long id : audioIds) {
            for (AudioDto audio : audios) {
                if (audio.getId().equals(id)) {
                    sortedAudios.add(audio);
                    break;
                }
            }
        }
        return sortedAudios;
    }

    private Map<Long, Double> getAudioScores(List<AudioDto> audios, double durationWeight, double ratingWeight, double extensionWeight, double sampleRateWeight) {
        return audios.stream().collect(Collectors.toMap(AudioDto::getId, audioDto -> {
            Float generalRating = 0f;
            double durationScore = 0;
            double extensionScore = 0;
            double sampleScore = 0;
            // get rating score
            try {
                ResponseEntity<RatingDto> ratingResponse = new RestTemplate().getForEntity("http://localhost:8081/api/rating/media/" + audioDto.getId(), RatingDto.class);
                RatingDto rating = ratingResponse.getBody();
                if (ratingResponse.getStatusCode().is2xxSuccessful() && rating != null) {
                    generalRating = rating.getGeneralRating();
                }
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                LOGGER.info(ex.getMessage());
            }
            // calculate duration score
            long duration = Optional.ofNullable(audioDto.getDuration()).orElse(0L);
            if (duration > 600 && duration < 1200){
                durationScore = 10;
            }
            // calculate extension score
            String fileName = Optional.ofNullable(audioDto.getFileName()).orElse("");
            String[] parts = fileName.split("\\.");
            String extension = parts[parts.length - 1];
            if (extension.equalsIgnoreCase("wav")) {
                extensionScore = 10;
            }
            // get sample score
            Float sampleRate = Optional.ofNullable(audioDto.getSampleRate()).orElse(0F);
            sampleScore = calculateSampleScore(sampleRate, sampleScore);
            return generalRating * ratingWeight + extensionScore * extensionWeight + sampleScore * sampleRateWeight + durationScore * durationWeight;
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
        return recommendationService.getRandomRecommendedMedia(AudioDto.class);
    }
}
