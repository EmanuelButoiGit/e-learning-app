package com.emanuel.mediaservice.converters;

import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.converters.MediaConverter;
import com.emanuel.starterlibrary.dtos.MediaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MediaConverterTest {
    private MediaConverter mediaConverter;
    private MediaEntity mediaEntity;
    private MediaDto mediaDto;

    @BeforeEach
    public void setUp() {
        mediaConverter = new MediaConverter();

        byte[] content = new byte[]{1, 2, 3};
        mediaEntity = new MediaEntity(1L, "Title", "Description", "fileName", "ext", new Date(), "mime", content, 500L);

        mediaDto = new MediaDto(1L, "Title", "Description", "fileName", "ext", new Date(), "mime", content, 500L);
    }

    @Test
    void shouldConvertEntityToDto() {
        MediaDto dto = mediaConverter.toDto(mediaEntity);
        assertEquals(mediaEntity.getId(), dto.getId());
        assertEquals(mediaEntity.getTitle(), dto.getTitle());
        assertEquals(mediaEntity.getDescription(), dto.getDescription());
        assertEquals(mediaEntity.getFileName(), dto.getFileName());
        assertEquals(mediaEntity.getExtension(), dto.getExtension());
        assertEquals(mediaEntity.getUploadDate(), dto.getUploadDate());
        assertEquals(mediaEntity.getMimeType(), dto.getMimeType());
        assertEquals(mediaEntity.getSize(), dto.getSize());
    }

    @Test
    void shouldConvertDtoToEntity() {
        MediaEntity entity = mediaConverter.toEntity(mediaDto);
        assertEquals(mediaDto.getId(), entity.getId());
        assertEquals(mediaDto.getTitle(), entity.getTitle());
        assertEquals(mediaDto.getDescription(), entity.getDescription());
        assertEquals(mediaDto.getFileName(), entity.getFileName());
        assertEquals(mediaDto.getExtension(), entity.getExtension());
        assertEquals(mediaDto.getUploadDate(), entity.getUploadDate());
        assertEquals(mediaDto.getMimeType(), entity.getMimeType());
        assertEquals(mediaDto.getSize(), entity.getSize());
    }
}
