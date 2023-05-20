package com.emanuel.mediaservice.controllers;

import com.emanuel.starterlibrary.dtos.AudioDto;
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
class AudioControllerTest extends BaseControllerTest {

    @Test
    void uploadAudioTest() {
        uploadAudio(TITLE, DESCRIPTION);
    }

    @SneakyThrows
    private AudioDto uploadAudio(String title, String description)  {
        // get the bytes of your real audio file
        byte[] audioBytes = Files.readAllBytes(Paths.get("src/test/resources/samples/test.mp3"));
        ByteArrayResource resource = new ByteArrayResource(audioBytes) {
            @Override
            public String getFilename() {
                return "test.mp3";
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
        ResponseEntity<AudioDto> result = rest.exchange("/api/audio", HttpMethod.POST, requestEntity, AudioDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        AudioDto audio = result.getBody();
        assertThat(audio).isNotNull();
        assertThat(audio.getTitle()).isEqualTo(title);
        assertThat(audio.getDescription()).isEqualTo(description);

        return audio;
    }

    @Test
    void getAudiosTest() {
        // assume
        uploadAudio(TITLE, DESCRIPTION);
        uploadAudio(TITLE + "2", DESCRIPTION + "2");

        // act
        ResponseEntity<List<AudioDto>> result = rest.exchange(
                "/api/audio",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<AudioDto> audioListFromRequest = result.getBody();
        assertThat(audioListFromRequest).isNotNull().hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void getAudioByIdTest() {
        // assume
        AudioDto audio = uploadAudio(TITLE, DESCRIPTION);
        final Long id = audio.getId();

        // act
        ResponseEntity<AudioDto> result = rest.getForEntity("/api/audio/" + id, AudioDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        AudioDto audioFromRequest = result.getBody();
        assertThat(audioFromRequest).isNotNull();
        assertThat(audioFromRequest.getTitle()).isEqualTo(TITLE);
        assertThat(audioFromRequest.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void deleteAudioTest() {
        // assume
        AudioDto audio = uploadAudio(TITLE, DESCRIPTION);
        final Long id = audio.getId();

        // act
        ResponseEntity<AudioDto> result = rest.exchange(
                "/api/audio/" + id,
                HttpMethod.DELETE,
                null,
                AudioDto.class
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        AudioDto audioFromRequest = result.getBody();
        assertThat(audioFromRequest).isNotNull();
        assertThat(audioFromRequest.getTitle()).isEqualTo(TITLE);
        assertThat(audioFromRequest.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void updateAudioTest() {
        // assume
        AudioDto audio = uploadAudio(TITLE, DESCRIPTION);
        final Long id = audio.getId();
        final String newTitle = TITLE + " new";
        final String newDescription = DESCRIPTION + " new";
        audio.setTitle(newTitle);
        audio.setDescription(newDescription);

        // act
        HttpEntity<AudioDto> requestEntity = new HttpEntity<>(audio);
        ResponseEntity<AudioDto> result = rest.exchange(
                "/api/audio/" + id,
                HttpMethod.PUT,
                requestEntity,
                AudioDto.class
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        AudioDto updatedAudioResult = result.getBody();
        assertThat(updatedAudioResult).isNotNull();
        assertThat(updatedAudioResult.getTitle()).isEqualTo(newTitle);
        assertThat(updatedAudioResult.getDescription()).isEqualTo(newDescription);

        // verify the audio was actually updated
        ResponseEntity<AudioDto> responseFromGetRequest = rest.getForEntity("/api/audio/" + id, AudioDto.class);
        AudioDto audioFromGetRequest = responseFromGetRequest.getBody();
        assertThat(audioFromGetRequest).isNotNull();
        assertThat(audioFromGetRequest.getTitle()).isEqualTo(newTitle);
        assertThat(audioFromGetRequest.getDescription()).isEqualTo(newDescription);
    }

    @Test
    void deleteAllAudiosTest() {
        // assume
        uploadAudio(TITLE, DESCRIPTION);

        // act
        ResponseEntity<Void> result = rest.exchange("/api/audio", HttpMethod.DELETE, null, Void.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // verify if all audios are deleted
        ResponseEntity<AudioDto[]> getAllResult = rest.getForEntity("/api/audio", AudioDto[].class);
        assertThat(getAllResult.getBody()).isNotNull().isEmpty();
    }

}
