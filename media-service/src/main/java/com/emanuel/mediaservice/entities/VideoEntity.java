package com.emanuel.mediaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "video")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VideoEntity extends MediaEntity {
    private Integer width;
    private Integer height;
    private Integer resolutionQuality;
    private Long duration;
    private Double aspectRatio;
    private Double fps;

    public VideoEntity(MediaEntity mediaEntity, Integer width, Integer height, Integer resolutionQuality,
                       Long duration, Double aspectRatio, Double fps) {
        super(mediaEntity.getId(), mediaEntity.getTitle(), mediaEntity.getDescription(),
                mediaEntity.getFileName(), mediaEntity.getExtension(), mediaEntity.getUploadDate(),
                mediaEntity.getMimeType(), mediaEntity.getContent(), mediaEntity.getSize());
        this.width = width;
        this.height = height;
        this.resolutionQuality = resolutionQuality;
        this.duration = duration;
        this.aspectRatio = aspectRatio;
        this.fps = fps;
    }
}
