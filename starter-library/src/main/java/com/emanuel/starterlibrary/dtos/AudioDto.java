package com.emanuel.starterlibrary.dtos;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AudioDto extends MediaDto {
    @NotNull
    @Min(value = 0)
    private Long duration;
    @NotNull
    @Min(value = 0)
    private Float sampleRate;

    public AudioDto(MediaDto mediaDto, Long duration, Float sampleRate) {
        super(mediaDto.getId(), mediaDto.getTitle(), mediaDto.getDescription(),
                mediaDto.getFileName(), mediaDto.getExtension(), mediaDto.getUploadDate(),
                mediaDto.getMimeType(), mediaDto.getContent(), mediaDto.getSize());
        this.duration = duration;
        this.sampleRate = sampleRate;
    }
}
