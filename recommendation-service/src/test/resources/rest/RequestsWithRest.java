package com.emanuel.mediaservice;

import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.dtos.RatingDto;
import com.emanuel.starterlibrary.exceptions.DeserializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

class RequestsWithRest {

    @SuppressWarnings("unused")
    @SneakyThrows
    public <T extends MediaDto> List<T> getDtoListFromDatabaseWithRest(Class<T> mediaClassType, String mediaType) {
        ResponseEntity<List<T>> response = new RestTemplate()
                .exchange("http://localhost:8080/api/" + mediaType + "/", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});
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

    @SuppressWarnings("unused")
    private <T extends MediaDto> Float getMediaByRatingIdWithRest(T media, Float generalRating) {
        try {
            ResponseEntity<RatingDto> ratingResponse = new RestTemplate().getForEntity(RATING_MEDIA_ENDPOINT + media.getId(), RatingDto.class);
            RatingDto rating = ratingResponse.getBody();
            if (ratingResponse.getStatusCode().is2xxSuccessful() && rating != null) {
                generalRating = rating.getGeneralRating();
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.info(e.getMessage());
        }
        return generalRating;
    }

}