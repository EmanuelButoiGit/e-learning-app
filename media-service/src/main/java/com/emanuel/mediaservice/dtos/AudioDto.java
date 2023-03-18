package com.emanuel.mediaservice.dtos;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AudioDto extends MediaDto{
    private Long duration;
    private Float sampleRate;
    @Builder
    public AudioDto(Long id, String title, String description, String fileName,
                       Date uploadDate, String mimeType, byte[] content, Long size,
                       Long duration, Float sampleRate) {
        super(id, title, description, fileName, uploadDate, mimeType, content, size);
        this.duration = duration;
        this.sampleRate = sampleRate;
    }
}
