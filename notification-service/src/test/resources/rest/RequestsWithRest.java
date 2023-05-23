package com.emanuel.mediaservice;

import com.emanuel.starterlibrary.exceptions.DeserializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

class RequestsWithRest {

    @SuppressWarnings("unused")
    private List<String> getTopMediaWithRest() throws DeserializationException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8082/api/recommendation/media/top")
                .queryParam("numberOfMedias", 10);
        ResponseEntity<List<String>> response = new RestTemplate()
                .exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        List<String> medias;
        ObjectMapper mapper = new ObjectMapper();
        try {
            medias = mapper.readValue(mapper.writeValueAsString(response.getBody()),
                    mapper.getTypeFactory().constructCollectionType(List.class, String.class));
            if (medias == null) {
                throw new NullPointerException("The list is empty!");
            }
        } catch (IOException e) {
            throw new DeserializationException("Failed to deserialize response: " + e.getMessage());
        }
        return medias;
    }

}