package com.emanuel.mediaservice.converters;

import com.emanuel.mediaservice.entities.DocumentEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.starterlibrary.dtos.DocumentDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentConverterTest {
    private DocumentConverter documentConverter;
    private DocumentEntity documentEntity;
    private DocumentDto documentDto;

    @BeforeEach
    public void setUp() {
        documentConverter = new DocumentConverter();

        byte[] content = new byte[]{1, 2, 3};
        documentEntity = new DocumentEntity(new MediaEntity(1L, "Title", "Description", "fileName", "ext", new Date(), "mime", content, 500L), 30);
        documentDto = new DocumentDto(new MediaDto(1L, "Title", "Description", "fileName", "ext", new Date(), "mime", content, 500L), 30);
    }

    @Test
    void shouldConvertEntityToDto() {
        DocumentDto dto = documentConverter.toDto(documentEntity);
        assertEquals(documentEntity.getId(), dto.getId());
        assertEquals(documentEntity.getTitle(), dto.getTitle());
        assertEquals(documentEntity.getDescription(), dto.getDescription());
        assertEquals(documentEntity.getFileName(), dto.getFileName());
        assertEquals(documentEntity.getExtension(), dto.getExtension());
        assertEquals(documentEntity.getUploadDate(), dto.getUploadDate());
        assertEquals(documentEntity.getMimeType(), dto.getMimeType());
        assertEquals(documentEntity.getSize(), dto.getSize());
        assertEquals(documentEntity.getNumberOfPages(), dto.getNumberOfPages());
    }

    @Test
    void shouldConvertDtoToEntity() {
        DocumentEntity entity = documentConverter.toEntity(documentDto);
        assertEquals(documentDto.getId(), entity.getId());
        assertEquals(documentDto.getTitle(), entity.getTitle());
        assertEquals(documentDto.getDescription(), entity.getDescription());
        assertEquals(documentDto.getFileName(), entity.getFileName());
        assertEquals(documentDto.getExtension(), entity.getExtension());
        assertEquals(documentDto.getUploadDate(), entity.getUploadDate());
        assertEquals(documentDto.getMimeType(), entity.getMimeType());
        assertEquals(documentDto.getSize(), entity.getSize());
        assertEquals(documentDto.getNumberOfPages(), entity.getNumberOfPages());
    }
}
