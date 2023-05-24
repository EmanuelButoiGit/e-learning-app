package com.emanuel.mediaservice;

import com.emanuel.mediaservice.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntitiesTest {

    private MediaEntity mediaEntity;
    private ImageEntity imageEntity;
    private final Date now = new Date();
    byte[] testContent = "Test content".getBytes();

    @BeforeEach
    public void setUp() {

        mediaEntity = new MediaEntity(1L, "Test Title", "Test Description",
                "TestFile", "extension", now, "mime", testContent, 50L);
    }

    @Test
    void testMediaEntity() {
        checkMediaFields(mediaEntity);
    }

    private <T extends MediaEntity> void checkMediaFields(T entity) {
        assertEquals(1L, entity.getId());
        assertEquals("Test Title", entity.getTitle());
        assertEquals("Test Description", entity.getDescription());
        assertEquals("TestFile", entity.getFileName());
        assertEquals("extension", entity.getExtension());
        assertEquals(now, entity.getUploadDate());
        assertEquals("mime", entity.getMimeType());
        assertEquals(testContent, entity.getContent());
        assertEquals(50L, entity.getSize());
    }

    @Test
    void testAudioEntity() {
        AudioEntity audioEntity = new AudioEntity(mediaEntity, 300L, 44100.0f);

        checkMediaFields(audioEntity);
        assertEquals(50L, audioEntity.getSize());
        assertEquals(300L, audioEntity.getDuration());
        assertEquals(44100.0f, audioEntity.getSampleRate());
    }

    @Test
    void testDocumentEntity() {
        DocumentEntity documentEntity = new DocumentEntity(mediaEntity, 10);

        checkMediaFields(documentEntity);
        assertEquals(50L, documentEntity.getSize());
        assertEquals(10, documentEntity.getNumberOfPages());
    }

    @Test
    void testImageEntity() {
        imageEntity = new ImageEntity(mediaEntity, 800, 600, 300);

        checkMediaFields(imageEntity);
        assertEquals(50L, imageEntity.getSize());
        assertEquals(800, imageEntity.getWidth());
        assertEquals(600, imageEntity.getHeight());
        assertEquals(300, imageEntity.getResolutionQuality());
    }

    @Test
    void testVideoEntity() {
        VideoEntity videoEntity = new VideoEntity(mediaEntity, 1920, 1080, 300, 120L, 16.0/9.0, 30.0);

        checkMediaFields(videoEntity);
        assertEquals(1920, videoEntity.getWidth());
        assertEquals(1080, videoEntity.getHeight());
        assertEquals(300, videoEntity.getResolutionQuality());
        assertEquals(120L, videoEntity.getDuration());
        assertEquals(16.0/9.0, videoEntity.getAspectRatio());
        assertEquals(30.0, videoEntity.getFps());
    }
}
