package com.emanuel.mediaservice.components;

import com.emanuel.mediaservice.dtos.ImageDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.ImageEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ImageConverter {
    public ImageDto toDto(ImageEntity imageEntity){
        ImageDto dto = new ImageDto();
        dto.setId(imageEntity.getId());
        dto.setTitle(imageEntity.getTitle());
        dto.setDescription(imageEntity.getDescription());
        dto.setFileName(imageEntity.getFileName());
        dto.setUploadDate(imageEntity.getUploadDate());
        dto.setMimeType(imageEntity.getMimeType());
        dto.setContent(imageEntity.getContent());
        dto.setSize(imageEntity.getSize());
        dto.setWidth(imageEntity.getWidth());
        dto.setHeight(imageEntity.getHeight());
        dto.setQuality(imageEntity.getQuality());
        return dto;
    }

    public ImageEntity toEntity(ImageDto imageDto){
        ImageEntity entity = new ImageEntity();
        entity.setId(imageDto.getId());
        entity.setTitle(imageDto.getTitle());
        entity.setDescription(imageDto.getDescription());
        entity.setFileName(imageDto.getFileName());
        entity.setUploadDate(imageDto.getUploadDate());
        entity.setMimeType(imageDto.getMimeType());
        entity.setContent(imageDto.getContent());
        entity.setSize(imageDto.getSize());
        entity.setWidth(imageDto.getWidth());
        entity.setHeight(imageDto.getHeight());
        entity.setQuality(imageDto.getQuality());
        return entity;
    }
}
