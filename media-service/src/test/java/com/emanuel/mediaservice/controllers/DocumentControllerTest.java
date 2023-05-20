package com.emanuel.mediaservice.controllers;

import com.emanuel.starterlibrary.dtos.DocumentDto;
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
class DocumentControllerTest extends BaseControllerTest {
    private static final String PDF_EXTENSION = "pdf";
    private static final String WORD_EXTENSION = "docx";
    @Test
    void uploadDocumentTest() {
        uploadDocument(TITLE, DESCRIPTION, PDF_EXTENSION);
        uploadDocument(TITLE + "2", DESCRIPTION + "2", WORD_EXTENSION);
    }

    @SneakyThrows
    private DocumentDto uploadDocument(String title, String description, String extension) {
        // get the bytes of your real image file
        byte[] imageBytes = Files.readAllBytes(Paths.get("src/test/resources/samples/test." + extension));
        ByteArrayResource resource = new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return "test." + extension;
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
        ResponseEntity<DocumentDto> result = rest.exchange("/api/document", HttpMethod.POST, requestEntity, DocumentDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        DocumentDto document = result.getBody();
        assertThat(document).isNotNull();
        assertThat(document.getTitle()).isEqualTo(title);
        assertThat(document.getDescription()).isEqualTo(description);

        return document;
    }

    @Test
    void getDocumentsTest() {
        // assume
        uploadDocument(TITLE, DESCRIPTION, PDF_EXTENSION);
        uploadDocument(TITLE + "2", DESCRIPTION + "2", WORD_EXTENSION);

        // act
        ResponseEntity<List<DocumentDto>> result = rest.exchange(
                "/api/document",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<DocumentDto> documentListFromRequest = result.getBody();
        assertThat(documentListFromRequest).isNotNull().hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void getDocumentByIdTest() {
        // assume
        DocumentDto document = uploadDocument(TITLE, DESCRIPTION, PDF_EXTENSION);
        final Long id = document.getId();

        // act
        ResponseEntity<DocumentDto> result = rest.getForEntity("/api/document/" + id, DocumentDto.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentDto documentFromRequest = result.getBody();
        assertThat(documentFromRequest).isNotNull();
        assertThat(documentFromRequest.getTitle()).isEqualTo(TITLE);
        assertThat(documentFromRequest.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void deleteDocumentTest() {
        // assume
        DocumentDto document = uploadDocument(TITLE, DESCRIPTION, PDF_EXTENSION);
        final Long id = document.getId();

        // act
        ResponseEntity<DocumentDto> result = rest.exchange(
                "/api/document/" + id,
                HttpMethod.DELETE,
                null,
                DocumentDto.class
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentDto documentFromRequest = result.getBody();
        assertThat(documentFromRequest).isNotNull();
        assertThat(documentFromRequest.getTitle()).isEqualTo(TITLE);
        assertThat(documentFromRequest.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    void updateDocumentTest() {
        // assume
        DocumentDto document = uploadDocument(TITLE, DESCRIPTION, PDF_EXTENSION);
        final Long id = document.getId();
        final String newTitle = TITLE + " new";
        final String newDescription = DESCRIPTION + " new";
        document.setTitle(newTitle);
        document.setDescription(newDescription);

        // act
        HttpEntity<DocumentDto> requestEntity = new HttpEntity<>(document);
        ResponseEntity<DocumentDto> result = rest.exchange(
                "/api/document/" + id,
                HttpMethod.PUT,
                requestEntity,
                DocumentDto.class
        );

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentDto updatedDocumentResult = result.getBody();
        assertThat(updatedDocumentResult).isNotNull();
        assertThat(updatedDocumentResult.getTitle()).isEqualTo(newTitle);
        assertThat(updatedDocumentResult.getDescription()).isEqualTo(newDescription);

        // verify the document was actually updated
        ResponseEntity<DocumentDto> responseFromGetRequest = rest.getForEntity("/api/document/" + id, DocumentDto.class);
        DocumentDto documentFromGetRequest = responseFromGetRequest.getBody();
        assertThat(documentFromGetRequest).isNotNull();
        assertThat(documentFromGetRequest.getTitle()).isEqualTo(newTitle);
        assertThat(documentFromGetRequest.getDescription()).isEqualTo(newDescription);
    }

    @Test
    void deleteAllDocumentsTest() {
        // assume
        uploadDocument(TITLE, DESCRIPTION, PDF_EXTENSION);

        // act
        ResponseEntity<Void> result = rest.exchange("/api/document", HttpMethod.DELETE, null, Void.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // verify if all documents are deleted
        ResponseEntity<DocumentDto[]> getAllResult = rest.getForEntity("/api/document", DocumentDto[].class);
        assertThat(getAllResult.getBody()).isNotNull().isEmpty();
    }

}
