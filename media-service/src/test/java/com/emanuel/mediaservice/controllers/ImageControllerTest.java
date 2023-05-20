package com.emanuel.mediaservice.controllers;

import com.emanuel.starterlibrary.dtos.ImageDto;
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
class ImageControllerTest extends BaseControllerTest {

    @Test
    void uploadImageTest() {
        uploadImage(TITLE, DESCRIPTION);
    }

    @SneakyThrows
    private ImageDto uploadImage(String title, String description)  {
        // get the bytes of your real image file
        byte[] imageBytes = Files.readAllBytes(Paths.get("src/test/resources/samples/test.jpg"));
        ByteArrayResource resource = new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return "test.jpg";
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
        ResponseEntity<ImageDto> result = rest.exchange("/api/image", HttpMethod.POST, requestEntity, ImageDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ImageDto image = result.getBody();
        assertThat(image).isNotNull();
        assertThat(image.getTitle()).isEqualTo(title);
        assertThat(image.getDescription()).isEqualTo(description);

        return image;
    }

    @Test
    void getImagesTest() {
        // assume
        uploadImage(TITLE, DESCRIPTION);
        uploadImage(TITLE + "2", DESCRIPTION + "2");

        // act
        ResponseEntity<List<ImageDto>> result = rest.exchange(
                "/api/image",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ImageDto> imageListFromRequest = result.getBody();
        assertThat(imageListFromRequest).isNotNull().hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void getImageByIdTest() {
        // assume
        ImageDto image = uploadImage(TITLE, DESCRIPTION);
        final Long id = image.getId();

        // act
        ResponseEntity<ImageDto> result = rest.getForEntity("/api/image/" + id, ImageDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        ImageDto imageFromRequest = result.getBody();
        assertThat(imageFromRequest).isNotNull();
        assertThat(imageFromRequest.getTitle()).isEqualTo(TITLE);
        assertThat(imageFromRequest.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void deleteImageTest() {
        // assume
        ImageDto image = uploadImage(TITLE, DESCRIPTION);
        final Long id = image.getId();

        // act
        ResponseEntity<ImageDto> result = rest.exchange(
                "/api/image/" + id,
                HttpMethod.DELETE,
                null,
                ImageDto.class
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        ImageDto imageFromRequest = result.getBody();
        assertThat(imageFromRequest).isNotNull();
        assertThat(imageFromRequest.getTitle()).isEqualTo(TITLE);
        assertThat(imageFromRequest.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void updateImageTest() {
        // assume
        ImageDto image = uploadImage(TITLE, DESCRIPTION);
        final Long id = image.getId();
        final String newTitle = TITLE + " new";
        final String newDescription = DESCRIPTION + " new";
        image.setTitle(newTitle);
        image.setDescription(newDescription);

        // act
        HttpEntity<ImageDto> requestEntity = new HttpEntity<>(image);
        ResponseEntity<ImageDto> result = rest.exchange(
                "/api/image/" + id,
                HttpMethod.PUT,
                requestEntity,
                ImageDto.class
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        ImageDto updatedImageResult = result.getBody();
        assertThat(updatedImageResult).isNotNull();
        assertThat(updatedImageResult.getTitle()).isEqualTo(newTitle);
        assertThat(updatedImageResult.getDescription()).isEqualTo(newDescription);

        // verify the image was actually updated
        ResponseEntity<ImageDto> responseFromGetRequest = rest.getForEntity("/api/image/" + id, ImageDto.class);
        ImageDto imageFromGetRequest = responseFromGetRequest.getBody();
        assertThat(imageFromGetRequest).isNotNull();
        assertThat(imageFromGetRequest.getTitle()).isEqualTo(newTitle);
        assertThat(imageFromGetRequest.getDescription()).isEqualTo(newDescription);
    }

    @Test
    void deleteAllImagesTest() {
        // assume
        uploadImage(TITLE, DESCRIPTION);

        // act
        ResponseEntity<Void> result = rest.exchange("/api/image", HttpMethod.DELETE, null, Void.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // verify if all images are deleted
        ResponseEntity<ImageDto[]> getAllResult = rest.getForEntity("/api/image", ImageDto[].class);
        assertThat(getAllResult.getBody()).isNotNull().isEmpty();
    }

}
