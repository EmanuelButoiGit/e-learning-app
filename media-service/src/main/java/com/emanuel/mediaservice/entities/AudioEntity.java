package com.emanuel.mediaservice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "audio")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AudioEntity extends MediaEntity{
    private Long duration;
    private Float sampleRate;
    @Builder
    public AudioEntity(Long id, String title, String description, String fileName,
                       Date uploadDate, String mimeType, byte[] content, Long size,
                       Long duration, Float sampleRate) {
        super(id, title, description, fileName, uploadDate, mimeType, content, size);
        this.duration = duration;
        this.sampleRate = sampleRate;
    }
}
