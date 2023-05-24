package com.emanuel.mediaservice.converters;

import com.emanuel.mediaservice.entities.VideoEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.starterlibrary.dtos.ImageDto;
import com.emanuel.starterlibrary.dtos.VideoDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VideoConverterTest {
    private VideoConverter videoConverter;
    private VideoEntity videoEntity;
    private VideoDto videoDto;

    @BeforeEach
    public void setUp() {
        videoConverter = new VideoConverter();

        byte[] content = new byte[]{1, 2, 3};
        videoEntity = new VideoEntity(new MediaEntity(1L, "Title", "Description", "fileName", "ext", new Date(), "mime", content, 500L),
                1000, 500, 1080, 120L, 16.0/9.0, 30.0);

        MediaDto mediaDto = new MediaDto(1L, "Title", "Description", "fileName", "ext", new Date(), "mime", content, 500L);
        ImageDto imageDto = new ImageDto(mediaDto, 1000, 500, 1080);
        videoDto = new VideoDto(imageDto, 120L, 16.0/9.0, 30.0);
    }

    @Test
    void shouldConvertEntityToDto() {
        VideoDto dto = videoConverter.toDto(videoEntity);
        assertEquals(videoEntity.getId(), dto.getId());
        assertEquals(videoEntity.getTitle(), dto.getTitle());
        assertEquals(videoEntity.getDescription(), dto.getDescription());
        assertEquals(videoEntity.getFileName(), dto.getFileName());
        assertEquals(videoEntity.getExtension(), dto.getExtension());
        assertEquals(videoEntity.getUploadDate(), dto.getUploadDate());
        assertEquals(videoEntity.getMimeType(), dto.getMimeType());
        assertEquals(videoEntity.getSize(), dto.getSize());
        assertEquals(videoEntity.getWidth(), dto.getWidth());
        assertEquals(videoEntity.getHeight(), dto.getHeight());
        assertEquals(videoEntity.getResolutionQuality(), dto.getResolutionQuality());
        assertEquals(videoEntity.getDuration(), dto.getDuration());
        assertEquals(videoEntity.getAspectRatio(), dto.getAspectRatio(), 0.00001);
        assertEquals(videoEntity.getFps(), dto.getFps(), 0.00001);
    }

    @Test
    void shouldConvertDtoToEntity() {
        VideoEntity entity = videoConverter.toEntity(videoDto);
        assertEquals(videoDto.getId(), entity.getId());
        assertEquals(videoDto.getTitle(), entity.getTitle());
        assertEquals(videoDto.getDescription(), entity.getDescription());
        assertEquals(videoDto.getFileName(), entity.getFileName());
        assertEquals(videoDto.getExtension(), entity.getExtension());
        assertEquals(videoDto.getUploadDate(), entity.getUploadDate());
        assertEquals(videoDto.getMimeType(), entity.getMimeType());
        assertEquals(videoDto.getSize(), entity.getSize());
        assertEquals(videoDto.getWidth(), entity.getWidth());
        assertEquals(videoDto.getHeight(), entity.getHeight());
        assertEquals(videoDto.getResolutionQuality(), entity.getResolutionQuality());
        assertEquals(videoDto.getDuration(), entity.getDuration());
        assertEquals(videoDto.getAspectRatio(), entity.getAspectRatio(), 0.00001);
        assertEquals(videoDto.getFps(), entity.getFps(), 0.00001);
    }

}
