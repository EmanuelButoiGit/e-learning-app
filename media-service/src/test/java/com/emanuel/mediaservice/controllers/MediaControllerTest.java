package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.proxies.NotificationServiceProxy;
import com.emanuel.mediaservice.services.MediaService;
import com.emanuel.starterlibrary.dtos.MediaDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class MediaControllerTest {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private TestRestTemplate rest;
    @MockBean
    private NotificationServiceProxy notificationServiceProxy;

    private final static String TITLE = "Test title";
    private final static String DESCRIPTION = "Test description";

    @Test
    void uploadMediaTest() {
        uploadMedia(TITLE, DESCRIPTION);
    }

    private MediaDto uploadMedia(String title, String description) {
        // assume
        ByteArrayResource resource = new ByteArrayResource("test data".getBytes()) {
            @Override
            public String getFilename() {
                return "test.txt";
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);
        body.add("title", title);
        body.add("description", description);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);

        Mockito.doNothing().when(notificationServiceProxy).sendNewMediaNotification(Mockito.anyString());

        // act
        ResponseEntity<MediaDto> result = rest.exchange("/api/media", HttpMethod.POST, requestEntity, MediaDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        MediaDto media = result.getBody();
        assertThat(media).isNotNull();
        assertThat(Objects.requireNonNull(media).getTitle()).isEqualTo(title);
        assertThat(media.getDescription()).isEqualTo(description);

        return media;
    }

    @Test
    void getMediaByIdTest() {
        // assume
        MediaDto media = uploadMedia(TITLE, DESCRIPTION);
        final Long id = media.getId();

        // act
        ResponseEntity<MediaDto> result = rest.getForEntity("/api/media/" + id, MediaDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(id);
    }

}
