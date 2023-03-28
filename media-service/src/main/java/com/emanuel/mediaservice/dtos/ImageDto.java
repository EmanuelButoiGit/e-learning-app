package com.emanuel.mediaservice.dtos;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageDto extends MediaDto {
    private Integer width;
    private Integer height;
    private Integer resolutionQuality;

    public ImageDto(MediaDto mediaDto, Integer width, Integer height, Integer resolutionQuality) {
        super(mediaDto.getId(), mediaDto.getTitle(), mediaDto.getDescription(),
                mediaDto.getFileName(), mediaDto.getExtension(), mediaDto.getUploadDate(),
                mediaDto.getMimeType(), mediaDto.getContent(), mediaDto.getSize());
        this.width = width;
        this.height = height;
        this.resolutionQuality = resolutionQuality;
    }

    public ImageDto(Long id, String title, String description, String fileName, String extension, Date uploadDate,
                    String mimeType, byte[] content, Long size, Integer width, Integer height, Integer resolutionQuality) {
        super(id, title, description, fileName, extension, uploadDate, mimeType, content, size);
        this.width = width;
        this.height = height;
        this.resolutionQuality = resolutionQuality;
    }
}
