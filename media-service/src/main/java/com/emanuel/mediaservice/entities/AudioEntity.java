package com.emanuel.mediaservice.entities;

import lombok.*;

import javax.persistence.*;

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
    public AudioEntity(MediaEntity mediaEntity, Long duration, Float sampleRate) {
        super(mediaEntity.getId(), mediaEntity.getTitle(), mediaEntity.getDescription(),
                mediaEntity.getFileName(), mediaEntity.getUploadDate(), mediaEntity.getMimeType(),
                mediaEntity.getContent(), mediaEntity.getSize());
        this.duration = duration;
        this.sampleRate = sampleRate;
    }
}
