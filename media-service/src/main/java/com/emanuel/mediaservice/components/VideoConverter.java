package com.emanuel.mediaservice.components;

import com.emanuel.mediaservice.dtos.VideoDto;
import com.emanuel.mediaservice.entities.VideoEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VideoConverter {
    public VideoDto toDto(VideoEntity videoEntity){
        VideoDto dto = new VideoDto();
        dto.setId(videoEntity.getId());
        dto.setTitle(videoEntity.getTitle());
        dto.setDescription(videoEntity.getDescription());
        dto.setFileName(videoEntity.getFileName());
        dto.setUploadDate(videoEntity.getUploadDate());
        dto.setMimeType(videoEntity.getMimeType());
        dto.setContent(videoEntity.getContent());
        dto.setSize(videoEntity.getSize());
        dto.setWidth(videoEntity.getWidth());
        dto.setHeight(videoEntity.getHeight());
        dto.setResolutionQuality(videoEntity.getResolutionQuality());
        dto.setDuration(videoEntity.getDuration());
        dto.setAspectRatio(videoEntity.getAspectRatio());
        dto.setFps(videoEntity.getFps());
        return dto;
    }

    public VideoEntity toEntity(VideoDto videoDto){
        VideoEntity entity = new VideoEntity();
        entity.setId(videoDto.getId());
        entity.setTitle(videoDto.getTitle());
        entity.setDescription(videoDto.getDescription());
        entity.setFileName(videoDto.getFileName());
        entity.setUploadDate(videoDto.getUploadDate());
        entity.setMimeType(videoDto.getMimeType());
        entity.setContent(videoDto.getContent());
        entity.setSize(videoDto.getSize());
        entity.setWidth(videoDto.getWidth());
        entity.setHeight(videoDto.getHeight());
        entity.setResolutionQuality(videoDto.getResolutionQuality());
        entity.setDuration(videoDto.getDuration());
        entity.setAspectRatio(videoDto.getAspectRatio());
        entity.setFps(videoDto.getFps());
        return entity;
    }
}
