package com.emanuel.recommendationservice.services;

import com.emanuel.recommendationservice.dtos.MediaDto;
import com.emanuel.recommendationservice.dtos.RatingDto;
import com.emanuel.recommendationservice.exceptions.DeserializationException;
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

@Service
public class RecommendationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationService.class);
    private final Random random = new Random();

    @SneakyThrows
    public <T> List<T> getDtoListFromDatabase(Class<T> mediaType) throws RuntimeException {
        ResponseEntity<List<T>> response = new RestTemplate()
                .exchange("http://localhost:8080/api/media/", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<T>>() {});
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<T> medias =mapper.readValue(mapper.writeValueAsString(response.getBody()),
                    mapper.getTypeFactory().constructCollectionType(List.class, mediaType));
            if (medias == null) {
                throw new NullPointerException("The table is empty!");
            }
            return medias;
        } catch (IOException e) {
            throw new DeserializationException("Failed to deserialize response: " + e.getMessage());
        }
    }

    public <T extends MediaDto> T getRandomRecommendedMedia(Class<T> mediaType) {
        int minRating = 5;
        List<T> medias = getDtoListFromDatabase(mediaType);
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
}
