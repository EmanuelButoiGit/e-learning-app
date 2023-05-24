package com.emanuel.mediaservice.converters;

import com.emanuel.mediaservice.entities.AudioEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.starterlibrary.dtos.AudioDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AudioConverterTest {
    private AudioConverter audioConverter;
    private AudioEntity audioEntity;
    private AudioDto audioDto;

    @BeforeEach
    public void setUp() {
        audioConverter = new AudioConverter();

        byte[] content = new byte[]{1, 2, 3};
        audioEntity = new AudioEntity(new MediaEntity(1L, "Title", "Description", "fileName", "ext", new Date(), "mime", content, 500L), 300L, 44100F);
        audioDto = new AudioDto(new MediaDto(1L, "Title", "Description", "fileName", "ext", new Date(), "mime", content, 500L), 300L, 44100F);
    }

    @Test
    void shouldConvertEntityToDto() {
        AudioDto dto = audioConverter.toDto(audioEntity);
        assertEquals(audioEntity.getId(), dto.getId());
        assertEquals(audioEntity.getTitle(), dto.getTitle());
        assertEquals(audioEntity.getDescription(), dto.getDescription());
        assertEquals(audioEntity.getFileName(), dto.getFileName());
        assertEquals(audioEntity.getExtension(), dto.getExtension());
        assertEquals(audioEntity.getUploadDate(), dto.getUploadDate());
        assertEquals(audioEntity.getMimeType(), dto.getMimeType());
        assertEquals(audioEntity.getSize(), dto.getSize());
        assertEquals(audioEntity.getDuration(), dto.getDuration());
        assertEquals(audioEntity.getSampleRate(), dto.getSampleRate());
    }

    @Test
    void shouldConvertDtoToEntity() {
        AudioEntity entity = audioConverter.toEntity(audioDto);
        assertEquals(audioDto.getId(), entity.getId());
        assertEquals(audioDto.getTitle(), entity.getTitle());
        assertEquals(audioDto.getDescription(), entity.getDescription());
        assertEquals(audioDto.getFileName(), entity.getFileName());
        assertEquals(audioDto.getExtension(), entity.getExtension());
        assertEquals(audioDto.getUploadDate(), entity.getUploadDate());
        assertEquals(audioDto.getMimeType(), entity.getMimeType());
        assertEquals(audioDto.getSize(), entity.getSize());
        assertEquals(audioDto.getDuration(), entity.getDuration());
        assertEquals(audioDto.getSampleRate(), entity.getSampleRate());
    }
}
