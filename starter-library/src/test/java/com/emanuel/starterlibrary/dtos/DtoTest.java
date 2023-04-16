package com.emanuel.starterlibrary.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class DtoTest {
    @Test
    void mediaDtoTest() {
        MediaDto mediaDto = new MediaDto(
                1L,
                "Title",
                "Description",
                "file.txt",
                "txt",
                new Date(),
                "text/plain",
                new byte[]{},
                0L
        );
        Assertions.assertEquals(1L, mediaDto.getId());
        Assertions.assertEquals("Title", mediaDto.getTitle());
        Assertions.assertEquals("Description", mediaDto.getDescription());
        Assertions.assertEquals("file.txt", mediaDto.getFileName());
        Assertions.assertEquals("txt", mediaDto.getExtension());
        Assertions.assertNotNull(mediaDto.getUploadDate());
        Assertions.assertEquals("text/plain", mediaDto.getMimeType());
        Assertions.assertArrayEquals(new byte[]{}, mediaDto.getContent());
        Assertions.assertEquals(0L, mediaDto.getSize());
    }
}
