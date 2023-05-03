package com.emanuel.starterlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VideoDto extends ImageDto {
    @NotNull
    @Min(value = 0)
    private Long duration;
    @NotNull
    @Min(value = 0)
    private Double aspectRatio;
    @NotNull
    @Min(value = 0)
    private Double fps;

    public VideoDto(ImageDto imageDto, Long duration, Double aspectRatio, Double fps) {
        super(imageDto.getId(), imageDto.getTitle(), imageDto.getDescription(),
                imageDto.getFileName(), imageDto.getExtension(), imageDto.getUploadDate(),
                imageDto.getMimeType(), imageDto.getContent(), imageDto.getSize(),
                imageDto.getWidth(), imageDto.getHeight(), imageDto.getResolutionQuality());
        this.duration = duration;
        this.aspectRatio = aspectRatio;
        this.fps = fps;
    }
}