package com.emanuel.mediaservice;

import lombok.SneakyThrows;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

class RequestsWithRest {

    @SneakyThrows
    @SuppressWarnings("unused")
    public void sendNotificationWithRest(String name){
        // This is how it used to be before adding FEIGN for load balancing
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8083/api/notification/new/media")
                    .queryParam("newMedia", "{name}");
            String url = builder.buildAndExpand(name).toUriString();
            new RestTemplate().postForEntity(url, Void.class, Void.class);
        } catch (RestClientException e) {
            throw new RestClientException("Can't send notification", e);
        }
    }

}