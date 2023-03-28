package com.emanuel.mediaservice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "document")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentEntity extends MediaEntity {
    private Integer numberOfPages;

    public DocumentEntity(MediaEntity mediaEntity, Integer numberOfPages) {
        super(mediaEntity.getId(), mediaEntity.getTitle(), mediaEntity.getDescription(),
                mediaEntity.getFileName(), mediaEntity.getExtension(), mediaEntity.getUploadDate(),
                mediaEntity.getMimeType(), mediaEntity.getContent(), mediaEntity.getSize());
        this.numberOfPages = numberOfPages;
    }
}
