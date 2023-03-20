package com.emanuel.mediaservice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageEntity extends MediaEntity {
    private Integer width;
    private Integer height;
    private Integer resolutionQuality;

    public ImageEntity(MediaEntity mediaEntity, Integer width, Integer height, Integer resolutionQuality) {
        super(mediaEntity.getId(), mediaEntity.getTitle(), mediaEntity.getDescription(),
                mediaEntity.getFileName(), mediaEntity.getUploadDate(), mediaEntity.getMimeType(),
                mediaEntity.getContent(), mediaEntity.getSize());
        this.width = width;
        this.height = height;
        this.resolutionQuality = resolutionQuality;
    }

    public ImageEntity(Long id, String title, String description, String fileName, Date uploadDate, String mimeType,
                    byte[] content, Long size, Integer width, Integer height, Integer resolutionQuality) {
        super(id, title, description, fileName, uploadDate, mimeType, content, size);
        this.width = width;
        this.height = height;
        this.resolutionQuality = resolutionQuality;
    }
}
