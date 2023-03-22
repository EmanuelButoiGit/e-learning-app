package com.emanuel.recommendationservice.services;

import com.emanuel.recommendationservice.dtos.AudioDto;
import com.emanuel.recommendationservice.dtos.MediaDto;
import com.emanuel.recommendationservice.dtos.RatingDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    public List<AudioDto> getRecommendedAudio(int nrOfAudios) {
        // add a 15% chance to get a random audio with a good content rating

        ResponseEntity<List<AudioDto>> audioResponse = new RestTemplate()
                .exchange("http://localhost:8080/api/media/", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<AudioDto>>() {});
        List<AudioDto> audios = audioResponse.getBody();
        if (audios == null) {
            throw new NullPointerException("The audio table is empty!");
        }
        // Set weights for each criterion
        double durationWeight = 0.15;
        double ratingWeight = 0.55;
        double extensionWeight = 0.15;
        double sampleRateWeight = 0.15;


        Map<Long, Double> scores = audios.stream().collect(Collectors.toMap(AudioDto::getId, audioDto -> {
            Float generalRating = 0f;
            ResponseEntity<RatingDto> ratingResponse = new RestTemplate().getForEntity("http://localhost:8081/api/rating/" + audioDto.getId(), RatingDto.class);
            RatingDto rating = ratingResponse.getBody();
            if (ratingResponse.getStatusCode().is2xxSuccessful() && rating != null) {
                generalRating = rating.getGeneralRating();
            }
            int durationScore = 0;
            Long duration = audioDto.getDuration();
            durationScore += duration;
            String fileName = audioDto.getFileName();
            String[] parts = fileName.split("\\.");
            String extension = parts[parts.length - 1];
            int extensionScore = 0;
            if (extension.equalsIgnoreCase("wav")) {
                // add score
                extensionScore += 1;
            }
            int sampleScore = 0;
            Float sampleRate = audioDto.getSampleRate();
            sampleScore += sampleRate;
            return generalRating * ratingWeight + extensionScore * extensionWeight + sampleScore * sampleRateWeight + durationScore * durationWeight;
        }));

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
}
