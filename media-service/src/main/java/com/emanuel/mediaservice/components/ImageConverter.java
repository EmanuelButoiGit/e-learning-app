package com.emanuel.mediaservice.components;

import com.emanuel.mediaservice.dtos.ImageDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.ImageEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ImageConverter extends MediaConverter {
    public ImageDto toDto(ImageEntity imageEntity){
        MediaDto media = super.toDto(imageEntity);
        return ImageDto.builder()
                .mediaDto(media)
                .width(imageEntity.getWidth())
                .height(imageEntity.getHeight())
                .resolutionQuality(imageEntity.getResolutionQuality())
                .build();
    }

    public ImageEntity toEntity(ImageDto imageDto){
        MediaEntity media = super.toEntity(imageDto);
        return ImageEntity.builder()
                .mediaEntity(media)
                .width(imageDto.getWidth())
                .height(imageDto.getHeight())
                .resolutionQuality(imageDto.getResolutionQuality())
                .build();
    }
}
