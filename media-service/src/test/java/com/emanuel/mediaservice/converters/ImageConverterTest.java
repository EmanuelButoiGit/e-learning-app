package com.emanuel.mediaservice.converters;

import com.emanuel.mediaservice.entities.ImageEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.starterlibrary.dtos.ImageDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageConverterTest {
    private ImageConverter imageConverter;
    private ImageEntity imageEntity;
    private ImageDto imageDto;

    @BeforeEach
    public void setUp() {
        imageConverter = new ImageConverter();

        byte[] content = new byte[]{1, 2, 3};
        imageEntity = new ImageEntity(new MediaEntity(1L, "Title", "Description", "fileName", "ext", new Date(), "mime", content, 500L), 1000, 500, 1080);
        imageDto = new ImageDto(new MediaDto(1L, "Title", "Description", "fileName", "ext", new Date(), "mime", content, 500L), 1000, 500, 1080);
    }

    @Test
    void shouldConvertEntityToDto() {
        ImageDto dto = imageConverter.toDto(imageEntity);
        assertEquals(imageEntity.getId(), dto.getId());
        assertEquals(imageEntity.getTitle(), dto.getTitle());
        assertEquals(imageEntity.getDescription(), dto.getDescription());
        assertEquals(imageEntity.getFileName(), dto.getFileName());
        assertEquals(imageEntity.getExtension(), dto.getExtension());
        assertEquals(imageEntity.getUploadDate(), dto.getUploadDate());
        assertEquals(imageEntity.getMimeType(), dto.getMimeType());
        assertEquals(imageEntity.getSize(), dto.getSize());
        assertEquals(imageEntity.getWidth(), dto.getWidth());
        assertEquals(imageEntity.getHeight(), dto.getHeight());
        assertEquals(imageEntity.getResolutionQuality(), dto.getResolutionQuality());
    }

    @Test
    void shouldConvertDtoToEntity() {
        ImageEntity entity = imageConverter.toEntity(imageDto);
        assertEquals(imageDto.getId(), entity.getId());
        assertEquals(imageDto.getTitle(), entity.getTitle());
        assertEquals(imageDto.getDescription(), entity.getDescription());
        assertEquals(imageDto.getFileName(), entity.getFileName());
        assertEquals(imageDto.getExtension(), entity.getExtension());
        assertEquals(imageDto.getUploadDate(), entity.getUploadDate());
        assertEquals(imageDto.getMimeType(), entity.getMimeType());
        assertEquals(imageDto.getSize(), entity.getSize());
        assertEquals(imageDto.getWidth(), entity.getWidth());
        assertEquals(imageDto.getHeight(), entity.getHeight());
        assertEquals(imageDto.getResolutionQuality(), entity.getResolutionQuality());
    }
}
