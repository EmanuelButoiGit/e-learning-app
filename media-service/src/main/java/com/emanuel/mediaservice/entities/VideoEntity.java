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
public class VideoEntity extends ImageEntity{
    private Long duration;
    private Double aspectRatio;
    private Double fps;

    public VideoEntity(ImageEntity imageEntity, Long duration, Double aspectRatio, Double fps) {
        super(imageEntity.getId(), imageEntity.getTitle(), imageEntity.getDescription(),
                imageEntity.getFileName(), imageEntity.getUploadDate(), imageEntity.getMimeType(),
                imageEntity.getContent(), imageEntity.getSize(), imageEntity.getWidth(),
                imageEntity.getHeight(), imageEntity.getResolutionQuality());
        this.duration = duration;
        this.aspectRatio = aspectRatio;
        this.fps = fps;
    }
}
