package com.emanuel.mediaservice;

import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.dtos.RatingDto;
import com.emanuel.starterlibrary.exceptions.EntityNotFoundException;
import lombok.SneakyThrows;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

class RequestsWithRest {

    @SneakyThrows
    @SuppressWarnings("unused")
    private void checkIfMediaIdIsValidWithRest(RatingDto rating) {
        try {
            new RestTemplate().getForEntity("http://localhost:8080/api/media/" + rating.getMediaId(), MediaDto.class);
        } catch (Exception e) {
            throw new EntityNotFoundException(rating.getMediaId());
        }
    }

}