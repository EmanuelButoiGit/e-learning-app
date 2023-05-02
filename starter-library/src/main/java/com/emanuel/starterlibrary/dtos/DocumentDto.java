package com.emanuel.starterlibrary.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentDto extends MediaDto {
    @NotNull
    @Positive
    private Integer numberOfPages;

    public DocumentDto(MediaDto mediaDto, Integer numberOfPages) {
        super(mediaDto.getId(), mediaDto.getTitle(), mediaDto.getDescription(),
                mediaDto.getFileName(), mediaDto.getExtension(), mediaDto.getUploadDate(),
                mediaDto.getMimeType(), mediaDto.getContent(), mediaDto.getSize());
        this.numberOfPages = numberOfPages;
    }
}
