package com.emanuel.mediaservice.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageDto extends MediaDto {
    private Integer width;
    private Integer height;
    private Integer resolutionQuality;
    @Builder
    public ImageDto(MediaDto mediaDto, Integer width, Integer height, Integer resolutionQuality) {
        super(mediaDto.getId(), mediaDto.getTitle(), mediaDto.getDescription(),
                mediaDto.getFileName(), mediaDto.getUploadDate(), mediaDto.getMimeType(),
                mediaDto.getContent(), mediaDto.getSize());
        this.width = width;
        this.height = height;
        this.resolutionQuality = resolutionQuality;
    }
}
