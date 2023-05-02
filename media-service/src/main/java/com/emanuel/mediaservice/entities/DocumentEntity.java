package com.emanuel.mediaservice.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "document")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentEntity extends MediaEntity {
    @NotNull
    @Min(value = 0)
    private Integer numberOfPages;

    public DocumentEntity(MediaEntity mediaEntity, Integer numberOfPages) {
        super(mediaEntity.getId(), mediaEntity.getTitle(), mediaEntity.getDescription(),
                mediaEntity.getFileName(), mediaEntity.getExtension(), mediaEntity.getUploadDate(),
                mediaEntity.getMimeType(), mediaEntity.getContent(), mediaEntity.getSize());
        this.numberOfPages = numberOfPages;
    }
}
