package com.emanuel.mediaservice.controllers;

import com.emanuel.starterlibrary.dtos.VideoDto;
import lombok.SneakyThrows;
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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class VideoControllerTest extends BaseControllerTest {

    @Test
    void uploadVideoTest() {
        uploadVideo(TITLE, DESCRIPTION);
    }

    @SneakyThrows
    private VideoDto uploadVideo(String title, String description)  {
        // Get the bytes of your real video file
        byte[] videoBytes = Files.readAllBytes(Paths.get("src/test/resources/samples/test.avi"));
        ByteArrayResource resource = new ByteArrayResource(videoBytes) {
            @Override
            public String getFilename() {
                return "test.avi";
            }
        };

        // assume
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);
        body.add("title", title);
        body.add("description", description);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);

        Mockito.doNothing().when(notificationServiceProxy).sendNewMediaNotification(Mockito.anyString());

        // act
        ResponseEntity<VideoDto> result = rest.exchange("/api/video", HttpMethod.POST, requestEntity, VideoDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        VideoDto video = result.getBody();
        assertThat(video).isNotNull();
        assertThat(video.getTitle()).isEqualTo(title);
        assertThat(video.getDescription()).isEqualTo(description);

        return video;
    }

    @Test
    void getVideosTest() {
        // assume
        uploadVideo(TITLE, DESCRIPTION);
        uploadVideo(TITLE + "2", DESCRIPTION + "2");

        // act
        ResponseEntity<List<VideoDto>> result = rest.exchange(
                "/api/video",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<VideoDto> videoListFromRequest = result.getBody();
        assertThat(videoListFromRequest).isNotNull().hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void getVideoByIdTest() {
        // assume
        VideoDto video = uploadVideo(TITLE, DESCRIPTION);
        final Long id = video.getId();

        // act
        ResponseEntity<VideoDto> result = rest.getForEntity("/api/video/" + id, VideoDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        VideoDto videoFromRequest = result.getBody();
        assertThat(videoFromRequest).isNotNull();
        assertThat(videoFromRequest.getTitle()).isEqualTo(TITLE);
        assertThat(videoFromRequest.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void deleteVideoTest() {
        // assume
        VideoDto video = uploadVideo(TITLE, DESCRIPTION);
        final Long id = video.getId();

        // act
        ResponseEntity<VideoDto> result = rest.exchange(
                "/api/video/" + id,
                HttpMethod.DELETE,
                null,
                VideoDto.class
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        VideoDto videoFromRequest = result.getBody();
        assertThat(videoFromRequest).isNotNull();
        assertThat(videoFromRequest.getTitle()).isEqualTo(TITLE);
        assertThat(videoFromRequest.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void updateVideoTest() {
        // assume
        VideoDto video = uploadVideo(TITLE, DESCRIPTION);
        final Long id = video.getId();
        final String newTitle = TITLE + " new";
        final String newDescription = DESCRIPTION + " new";
        video.setTitle(newTitle);
        video.setDescription(newDescription);

        // act
        HttpEntity<VideoDto> requestEntity = new HttpEntity<>(video);
        ResponseEntity<VideoDto> result = rest.exchange(
                "/api/video/" + id,
                HttpMethod.PUT,
                requestEntity,
                VideoDto.class
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        VideoDto updatedVideoResult = result.getBody();
        assertThat(updatedVideoResult).isNotNull();
        assertThat(updatedVideoResult.getTitle()).isEqualTo(newTitle);
        assertThat(updatedVideoResult.getDescription()).isEqualTo(newDescription);

        // verify the video was actually updated
        ResponseEntity<VideoDto> responseFromGetRequest = rest.getForEntity("/api/video/" + id, VideoDto.class);
        VideoDto videoFromGetRequest = responseFromGetRequest.getBody();
        assertThat(videoFromGetRequest).isNotNull();
        assertThat(videoFromGetRequest.getTitle()).isEqualTo(newTitle);
        assertThat(videoFromGetRequest.getDescription()).isEqualTo(newDescription);
    }

    @Test
    void deleteAllVideosTest() {
        // assume
        uploadVideo(TITLE, DESCRIPTION);

        // act
        ResponseEntity<Void> result = rest.exchange("/api/video", HttpMethod.DELETE, null, Void.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // verify if all videos are deleted
        ResponseEntity<VideoDto[]> getAllResult = rest.getForEntity("/api/video", VideoDto[].class);
        assertThat(getAllResult.getBody()).isNotNull().isEmpty();
    }

}
