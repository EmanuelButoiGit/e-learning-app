package com.emanuel.starterlibrary.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageDto extends MediaDto {
    @NotNull
    @Positive
    private Integer width;
    @NotNull
    @Positive
    private Integer height;
    @NotNull
    @Positive
    private Integer resolutionQuality;

    public ImageDto(MediaDto mediaDto, Integer width, Integer height, Integer resolutionQuality) {
        super(mediaDto.getId(), mediaDto.getTitle(), mediaDto.getDescription(),
                mediaDto.getFileName(), mediaDto.getExtension(), mediaDto.getUploadDate(),
                mediaDto.getMimeType(), mediaDto.getContent(), mediaDto.getSize());
        this.width = width;
        this.height = height;
        this.resolutionQuality = resolutionQuality;
    }

    @SuppressWarnings("squid:S00107")
    public ImageDto(Long id, String title, String description, String fileName, String extension, Date uploadDate,
                    String mimeType, byte[] content, Long size, Integer width, Integer height, Integer resolutionQuality) {
        super(id, title, description, fileName, extension, uploadDate, mimeType, content, size);
        this.width = width;
        this.height = height;
        this.resolutionQuality = resolutionQuality;
    }
}
