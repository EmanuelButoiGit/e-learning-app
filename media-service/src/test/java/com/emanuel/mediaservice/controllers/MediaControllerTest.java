package com.emanuel.mediaservice.controllers;

import com.emanuel.starterlibrary.dtos.MediaDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class MediaControllerTest extends BaseControllerTest {

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
        assertThat(media.getTitle()).isEqualTo(title);
        assertThat(media.getDescription()).isEqualTo(description);

        return media;
    }

    @Test
    void getMediasTest() {
        // assume
        uploadMedia(TITLE, DESCRIPTION);
        uploadMedia(TITLE + "2", DESCRIPTION + "2");

        // act
        ResponseEntity<List<MediaDto>> result = rest.exchange(
                "/api/media",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<MediaDto> mediaListFromRequest = result.getBody();
        assertThat(mediaListFromRequest).isNotNull().hasSizeGreaterThanOrEqualTo(2);
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
        MediaDto mediaFromRequest = result.getBody();
        assertThat(mediaFromRequest).isNotNull();
        assertThat(mediaFromRequest.getTitle()).isEqualTo(TITLE);
        assertThat(mediaFromRequest.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void deleteMediaTest() {
        // assume
        MediaDto media = uploadMedia(TITLE, DESCRIPTION);
        final Long id = media.getId();

        // act
        ResponseEntity<MediaDto> result = rest.exchange(
                "/api/media/" + id,
                HttpMethod.DELETE,
                null,
                MediaDto.class
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        MediaDto mediaFromRequest = result.getBody();
        assertThat(mediaFromRequest).isNotNull();
        assertThat(mediaFromRequest.getTitle()).isEqualTo(TITLE);
        assertThat(mediaFromRequest.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void updateMediaTest() {
        // assume
        MediaDto media = uploadMedia(TITLE, DESCRIPTION);
        final Long id = media.getId();
        final String newTitle = TITLE + " new";
        final String newDescription = DESCRIPTION + " new";
        media.setTitle(newTitle);
        media.setDescription(newDescription);

        // act
        HttpEntity<MediaDto> requestEntity = new HttpEntity<>(media);
        ResponseEntity<MediaDto> result = rest.exchange(
                "/api/media/" + id,
                HttpMethod.PUT,
                requestEntity,
                MediaDto.class
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        MediaDto updatedMediaResult = result.getBody();
        assertThat(updatedMediaResult).isNotNull();
        assertThat(updatedMediaResult.getTitle()).isEqualTo(newTitle);
        assertThat(updatedMediaResult.getDescription()).isEqualTo(newDescription);

        // verify the media was actually updated
        ResponseEntity<MediaDto> responseFromGetRequest = rest.getForEntity("/api/media/" + id, MediaDto.class);
        MediaDto mediaFromGetRequest = responseFromGetRequest.getBody();
        assertThat(mediaFromGetRequest).isNotNull();
        assertThat(mediaFromGetRequest.getTitle()).isEqualTo(newTitle);
        assertThat(mediaFromGetRequest.getDescription()).isEqualTo(newDescription);
    }

    @Test
    void deleteAllMediasTest() {
        // assume
        uploadMedia(TITLE, DESCRIPTION);

        // act
        ResponseEntity<Void> result = rest.exchange("/api/media", HttpMethod.DELETE, null, Void.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // verify if all medias are deleted
        ResponseEntity<MediaDto[]> getAllResult = rest.getForEntity("/api/media", MediaDto[].class);
        assertThat(getAllResult.getBody()).isNotNull().isEmpty();
    }

}
