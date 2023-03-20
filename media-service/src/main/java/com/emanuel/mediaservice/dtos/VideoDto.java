package com.emanuel.mediaservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VideoDto extends ImageDto {
    private Long duration;
    private Double aspectRatio;
    private Double fps;

    public VideoDto(ImageDto imageDto, Long duration, Double aspectRatio, Double fps) {
        super(imageDto.getId(), imageDto.getTitle(), imageDto.getDescription(),
                imageDto.getFileName(), imageDto.getUploadDate(), imageDto.getMimeType(),
                imageDto.getContent(), imageDto.getSize(), imageDto.getWidth(),
                imageDto.getHeight(), imageDto.getResolutionQuality());
        this.duration = duration;
        this.aspectRatio = aspectRatio;
        this.fps = fps;
    }
}