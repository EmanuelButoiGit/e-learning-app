package com.emanuel.mediaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Entity
@Table(name = "video")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VideoEntity extends MediaEntity {
    @NotNull
    @Min(value = 0)
    private Integer width;
    @NotNull
    @Min(value = 0) 
    private Integer height;
    @NotNull
    @Min(value = 0) 
    private Integer resolutionQuality;
    @NotNull
    @Min(value = 0) 
    private Long duration;
    @NotNull
    @Min(value = 0) 
    private Double aspectRatio;
    @NotNull
    @Min(value = 0) 
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
