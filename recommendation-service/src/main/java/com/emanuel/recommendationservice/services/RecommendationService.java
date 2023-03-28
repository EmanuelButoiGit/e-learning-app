package com.emanuel.recommendationservice.services;

import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.dtos.RatingDto;
import com.emanuel.starterlibrary.exceptions.DeserializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationService.class);
    private final Random random = new Random();

    @SneakyThrows
    public <T> List<T> getDtoListFromDatabase(Class<T> mediaClassType, String mediaType) {
        ResponseEntity<List<T>> response = new RestTemplate()
                .exchange("http://localhost:8080/api/" + mediaType + "/", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<T>>() {});
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<T> medias =mapper.readValue(mapper.writeValueAsString(response.getBody()),
                    mapper.getTypeFactory().constructCollectionType(List.class, mediaClassType));
            if (medias == null) {
                throw new NullPointerException("The table is empty!");
            }
            return medias;
        } catch (IOException e) {
            throw new DeserializationException("Failed to deserialize response: " + e.getMessage());
        }
    }

    public  <T extends MediaDto> Float getRating(T media, Float generalRating) {
        try {
            ResponseEntity<RatingDto> ratingResponse = new RestTemplate().getForEntity("http://localhost:8081/api/rating/media/" + media.getId(), RatingDto.class);
            RatingDto rating = ratingResponse.getBody();
            if (ratingResponse.getStatusCode().is2xxSuccessful() && rating != null) {
                generalRating = rating.getGeneralRating();
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            LOGGER.info(ex.getMessage());
        }
        return generalRating;
    }

    public <T extends MediaDto> List<T> getSortedMedia(int numberOfMedias, List<T> medias, Map<Long, Double> scores) {
        List<Map.Entry<Long, Double>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        List<Long> mediaIds = sortedScores.stream()
                .limit(numberOfMedias)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<T> sortedMedia = new ArrayList<>();
        for (Long id : mediaIds) {
            for (T media : medias) {
                if (media.getId().equals(id)) {
                    sortedMedia.add(media);
                    break;
                }
            }
        }
        return sortedMedia;
    }

    public <T extends MediaDto> T getRandomRecommendedMedia(Class<T> mediaClassType, String mediaType) {
        int minRating = 5;
        List<T> medias = getDtoListFromDatabase(mediaClassType, mediaType);
        Float generalRating = 0f;
        List<T> passMedias = new ArrayList<>();
        for (T media : medias) {
            try {
                ResponseEntity<RatingDto> ratingResponse = new RestTemplate()
                        .getForEntity("http://localhost:8081/api/rating/media/" + media.getId(), RatingDto.class);
                RatingDto rating = ratingResponse.getBody();
                if (ratingResponse.getStatusCode().is2xxSuccessful() && rating != null) {
                    generalRating = rating.getGeneralRating();
                }
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                LOGGER.info(ex.getMessage());
            }
            if (generalRating >= minRating) {
                passMedias.add(media);
            }
        }
        if (passMedias.isEmpty()) {
            throw new UnsupportedOperationException("No media has a rating above " + minRating);
        }
        int randomNumber = random.nextInt(passMedias.size());
        return passMedias.get(randomNumber);
    }

    public Integer calculateResolutionQuality(Integer quality) {
        int score = 0;
        if (quality == 144) {
            score = 3;
        } else if (quality == 360) {
            score = 4;
        } else if (quality == 480) {
            score = 5;
        } else if (quality == 720) {
            score = 6;
        } else if (quality == 1080) {
            score = 7;
        } else if (quality == 1440) {
            score = 8;
        } else if (quality == 2160) {
            score = 9;
        } else if (quality == 4320) {
            score = 10;
        }
        return score;
    }
}
