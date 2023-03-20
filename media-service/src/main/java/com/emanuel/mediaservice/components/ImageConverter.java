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
        return new ImageDto(media, imageEntity.getWidth(), imageEntity.getHeight(), imageEntity.getResolutionQuality());
    }

    public ImageEntity toEntity(ImageDto imageDto){
        MediaEntity media = super.toEntity(imageDto);
        return new ImageEntity(media, imageDto.getWidth(), imageDto.getHeight(), imageDto.getResolutionQuality());
    }
}
