package com.emanuel.mediaservice.controllers;

import com.emanuel.starterlibrary.dtos.AudioDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class AudioControllerTest extends BaseTestController {

    @Test
    void uploadAudioTest() throws IOException {
        // Get the bytes of your real audio file
        byte[] audioBytes = Files.readAllBytes(Paths.get("src/test/resources/samples/test.mp3"));
        ByteArrayResource resource = new ByteArrayResource(audioBytes) {
            @Override
            public String getFilename() {
                return "test.mp3";
            }
        };

        uploadAudio(TITLE, DESCRIPTION, resource);
    }

    private AudioDto uploadAudio(String title, String description, ByteArrayResource resource) {
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
}
