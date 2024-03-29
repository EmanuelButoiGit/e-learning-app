package com.emanuel.mediaservice.converters;

import com.emanuel.mediaservice.entities.ImageEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.starterlibrary.dtos.ImageDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
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
