package com.emanuel.recommendationservice.services;

import com.emanuel.recommendationservice.dtos.AudioDto;
import com.emanuel.recommendationservice.dtos.MediaDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RecommendationService {
    public List<AudioDto> getRecommendedAudio() {
        // TO DO: implementation
        ResponseEntity<List<AudioDto>> audioResponse = new RestTemplate()
                .exchange("http://localhost:8080/api/media/", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<AudioDto>>() {});
        List<AudioDto> audios = audioResponse.getBody();

        // Set weights for each criterion
        double durationWeight = 0.15;
        double ratingWeight = 0.55;
        double extensionWeight = 0.15;
        double sampleRateWeight = 0.15;

/*        audios.stream().map(audioDto -> {
            Long id = audioDto.getId();

        })*/
        return audios;
    }
}
