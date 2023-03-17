package com.emanuel.mediaservice.components;

import com.emanuel.mediaservice.dtos.AudioDto;
import com.emanuel.mediaservice.entities.AudioEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AudioConverter {
    public AudioDto toDto(AudioEntity audioEntity){
        AudioDto dto = new AudioDto();
        dto.setId(audioEntity.getId());
        dto.setTitle(audioEntity.getTitle());
        dto.setDescription(audioEntity.getDescription());
        dto.setFileName(audioEntity.getFileName());
        dto.setUploadDate(audioEntity.getUploadDate());
        dto.setMimeType(audioEntity.getMimeType());
        dto.setContent(audioEntity.getContent());
        dto.setSize(audioEntity.getSize());
        dto.setDuration(audioEntity.getDuration());
        dto.setSampleRate(audioEntity.getSampleRate());
        return dto;
    }

    public AudioEntity toEntity(AudioDto audioDto){
        AudioEntity entity = new AudioEntity();
        entity.setId(audioDto.getId());
        entity.setTitle(audioDto.getTitle());
        entity.setDescription(audioDto.getDescription());
        entity.setFileName(audioDto.getFileName());
        entity.setUploadDate(audioDto.getUploadDate());
        entity.setMimeType(audioDto.getMimeType());
        entity.setContent(audioDto.getContent());
        entity.setSize(audioDto.getSize());
        entity.setDuration(audioDto.getDuration());
        entity.setSampleRate(audioDto.getSampleRate());
        return entity;
    }
}
