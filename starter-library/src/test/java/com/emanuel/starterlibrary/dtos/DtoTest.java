package com.emanuel.starterlibrary.dtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class DtoTest {
    private final MediaDto mediaDto = new MediaDto(
            1L,
            "Title",
            "Description",
            "file.txt",
            "txt",
            new Date(),
            "text/plain",
            new byte[]{},
            2048L
    );

    private final ImageDto imageDto = new ImageDto(
            mediaDto,
            480,
            480,
            480
    );

    @Test
    void mediaDtoTest() {
        MediaDto dto = new MediaDto();
        Assertions.assertNotNull(dto, "Constructor with no args is working");
        checkMediaFields(this.mediaDto);
    }

    private <T extends MediaDto> void checkMediaFields(T dto) {
        Assertions.assertEquals(1L, dto.getId());
        Assertions.assertEquals("Title", dto.getTitle());
        Assertions.assertEquals("Description", dto.getDescription());
        Assertions.assertEquals("file.txt", dto.getFileName());
        Assertions.assertEquals("txt", dto.getExtension());
        Assertions.assertNotNull(dto.getUploadDate());
        Assertions.assertEquals("text/plain", dto.getMimeType());
        Assertions.assertArrayEquals(new byte[]{}, dto.getContent());
        Assertions.assertEquals(2048L, dto.getSize());
        // update fields
        mediaDto.setId(1L);
        mediaDto.setTitle("My Title");
        mediaDto.setDescription("My Description");
        mediaDto.setFileName("my-file-name");
        mediaDto.setExtension(".png");
        mediaDto.setUploadDate(new Date());
        mediaDto.setMimeType("image/png");
        mediaDto.setContent(new byte[] { 0x01, 0x02, 0x03 });
        mediaDto.setSize(1024L);
        Assertions.assertEquals(1L, mediaDto.getId());
        Assertions.assertEquals("My Title", mediaDto.getTitle());
        Assertions.assertEquals("My Description", mediaDto.getDescription());
        Assertions.assertEquals("my-file-name", mediaDto.getFileName());
        Assertions.assertEquals(".png", mediaDto.getExtension());
        Assertions.assertNotNull(mediaDto.getUploadDate());
        Assertions.assertEquals("image/png", mediaDto.getMimeType());
        Assertions.assertArrayEquals(new byte[] { 0x01, 0x02, 0x03 }, mediaDto.getContent());
        Assertions.assertEquals(1024L, mediaDto.getSize());
    }

    @Test
    void audioDtoTest() {
        AudioDto audioDto = new AudioDto(
                mediaDto,
                10000L,
                3204.1024F
        );
        checkMediaFields(audioDto);
        Assertions.assertEquals(10000L, audioDto.getDuration());
        Assertions.assertEquals(3204.1024F, audioDto.getSampleRate());
    }

    @Test
    void documentDtoTest() {
        DocumentDto documentDto = new DocumentDto(
                mediaDto,
                10
        );
        checkMediaFields(documentDto);
        Assertions.assertEquals(10, documentDto.getNumberOfPages());
    }

    @Test
    void imageDtoTest() {
        checkImageFields(this.imageDto);
    }

    private void checkImageFields(ImageDto imageDto) {
        checkMediaFields(imageDto);
        Assertions.assertEquals(480, imageDto.getWidth());
        Assertions.assertEquals(480, imageDto.getHeight());
        Assertions.assertEquals(480, imageDto.getResolutionQuality());
    }

    @Test
    void videoDtoTest() {
        VideoDto videoDto = new VideoDto(
                this.imageDto,
                10000L,
                1.0,
                30.0
        );
        checkImageFields(videoDto);
        Assertions.assertEquals(10000L, videoDto.getDuration());
        Assertions.assertEquals(1.0, videoDto.getAspectRatio());
        Assertions.assertEquals(30.0, videoDto.getFps());
    }

    @Test
    void ratingDtoTest(){
        RatingDto ratingDto = new RatingDto(
                1L,
                1L,
                "A nice media",
                "I think it was the most informative",
                10.0F,
                10.0F,
                10.0F,
                10.0F,
                10.0F,
                10.0F,
                10.0F
        );
        Assertions.assertEquals(1L, ratingDto.getId());
        Assertions.assertEquals(1L, ratingDto.getMediaId());
        Assertions.assertEquals("A nice media", ratingDto.getTitle());
        Assertions.assertEquals("I think it was the most informative", ratingDto.getDescription());
        Assertions.assertEquals(10.0F, ratingDto.getGeneralRating());
        Assertions.assertEquals(10.0F, ratingDto.getTutorRating());
        Assertions.assertEquals(10.0F, ratingDto.getContentRating());
        Assertions.assertEquals(10.0F, ratingDto.getContentStructureRating());
        Assertions.assertEquals(10.0F, ratingDto.getPresentationRating());
        Assertions.assertEquals(10.0F, ratingDto.getEngagementRating());
        Assertions.assertEquals(10.0F, ratingDto.getDifficultyRating());
    }

    @Test
    void metricDtoTest(){
        String metricName = "http.server.requests";
        String metricDescription = "Number of HTTP requests received by the server";
        String metricEmoji = "&#128214;";
        MetricDto metricDto = new MetricDto(
                metricName,
                metricDescription,
                metricEmoji
        );
        Assertions.assertEquals(metricName, metricDto.getName());
        Assertions.assertEquals(metricDescription, metricDto.getDescription());
        Assertions.assertEquals(metricEmoji, metricDto.getEmoji());
    }
}
