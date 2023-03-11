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
        dto.setUploadDate(mediaEntity.getUploadDate());
        dto.setMimeType(mediaEntity.getMimeType());
        dto.setContent(mediaEntity.getContent());
        return dto;
    }
}
