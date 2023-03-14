package com.emanuel.mediaservice.components;

import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.MediaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MediaConverter {
    public MediaDto toDto(MediaEntity mediaEntity){
        MediaDto dto = new MediaDto();
        dto.setId(mediaEntity.getId());
        dto.setTitle(mediaEntity.getTitle());
        dto.setDescription(mediaEntity.getDescription());
        dto.setFileName(mediaEntity.getFileName());
        dto.setUploadDate(mediaEntity.getUploadDate());
        dto.setMimeType(mediaEntity.getMimeType());
        dto.setContent(mediaEntity.getContent());
        dto.setSize(mediaEntity.getSize());
        return dto;
    }

    public MediaEntity toEntity(MediaDto mediaDto){
        MediaEntity entity = new MediaEntity();
        entity.setId(mediaDto.getId());
        entity.setTitle(mediaDto.getTitle());
        entity.setDescription(mediaDto.getDescription());
        entity.setFileName(mediaDto.getFileName());
        entity.setUploadDate(mediaDto.getUploadDate());
        entity.setMimeType(mediaDto.getMimeType());
        entity.setContent(mediaDto.getContent());
        entity.setSize(mediaDto.getSize());
        return entity;
    }
}
