package com.emanuel.mediaservice.components;

import com.emanuel.mediaservice.dtos.AudioDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.AudioEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AudioConverter extends MediaConverter{
    public AudioDto toDto(AudioEntity audioEntity){
        MediaDto media = super.toDto(audioEntity);
        return AudioDto.builder()
                .id(media.getId())
                .title(media.getTitle())
                .description(media.getDescription())
                .fileName(media.getFileName())
                .uploadDate(media.getUploadDate())
                .mimeType(media.getMimeType())
                .content(media.getContent())
                .size(media.getSize())
                .duration(audioEntity.getDuration())
                .sampleRate(audioEntity.getSampleRate())
                .build();
    }

    public AudioEntity toEntity(AudioDto audioDto){
        MediaEntity media = super.toEntity(audioDto);
        return AudioEntity.builder()
                .id(media.getId())
                .title(media.getTitle())
                .description(media.getDescription())
                .fileName(media.getFileName())
                .uploadDate(media.getUploadDate())
                .mimeType(media.getMimeType())
                .content(media.getContent())
                .size(media.getSize())
                .duration(audioDto.getDuration())
                .sampleRate(audioDto.getSampleRate())
                .build();
    }
}
