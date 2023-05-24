package com.emanuel.mediaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "audio")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AudioEntity extends MediaEntity {
    @NotNull
    @Min(value = 0)
    private Long duration;
    @NotNull
    @Min(value = 0) 
    private Float sampleRate;

    public AudioEntity(MediaEntity mediaEntity, Long duration, Float sampleRate) {
        super(mediaEntity.getId(), mediaEntity.getTitle(), mediaEntity.getDescription(),
                mediaEntity.getFileName(), mediaEntity.getExtension(), mediaEntity.getUploadDate(),
                mediaEntity.getMimeType(), mediaEntity.getContent(), mediaEntity.getSize());
        this.duration = duration;
        this.sampleRate = sampleRate;
    }
}
