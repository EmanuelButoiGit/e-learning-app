package com.emanuel.mediaservice.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentDto extends MediaDto {
    private Integer numberOfPages;

    public DocumentDto(MediaDto mediaDto, Integer numberOfPages) {
        super(mediaDto.getId(), mediaDto.getTitle(), mediaDto.getDescription(),
                mediaDto.getFileName(), mediaDto.getUploadDate(), mediaDto.getMimeType(),
                mediaDto.getContent(), mediaDto.getSize());
        this.numberOfPages = numberOfPages;
    }
}
