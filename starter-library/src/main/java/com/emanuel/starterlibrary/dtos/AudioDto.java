package com.emanuel.starterlibrary.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AudioDto extends MediaDto {
    private Long duration;
    private Float sampleRate;

    public AudioDto(MediaDto mediaDto, Long duration, Float sampleRate) {
        super(mediaDto.getId(), mediaDto.getTitle(), mediaDto.getDescription(),
                mediaDto.getFileName(), mediaDto.getExtension(), mediaDto.getUploadDate(),
                mediaDto.getMimeType(), mediaDto.getContent(), mediaDto.getSize());
        this.duration = duration;
        this.sampleRate = sampleRate;
    }
}