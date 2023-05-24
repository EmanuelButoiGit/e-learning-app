package com.emanuel.mediaservice.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageEntity extends MediaEntity {
    @NotNull
    @Min(value = 0)
    private Integer width;
    @NotNull
    @Min(value = 0) 
    private Integer height;
    @NotNull
    @Min(value = 0) 
    private Integer resolutionQuality;

    public ImageEntity(MediaEntity mediaEntity, Integer width, Integer height, Integer resolutionQuality) {
        super(mediaEntity.getId(), mediaEntity.getTitle(), mediaEntity.getDescription(),
                mediaEntity.getFileName(), mediaEntity.getExtension(), mediaEntity.getUploadDate(),
                mediaEntity.getMimeType(), mediaEntity.getContent(), mediaEntity.getSize());
        this.width = width;
        this.height = height;
        this.resolutionQuality = resolutionQuality;
    }
}
