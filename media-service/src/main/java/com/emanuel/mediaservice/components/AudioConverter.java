package com.emanuel.mediaservice.components;

import com.emanuel.mediaservice.dtos.AudioDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.AudioEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AudioConverter extends MediaConverter {
    public AudioDto toDto(AudioEntity audioEntity){
        MediaDto media = super.toDto(audioEntity);
        return new AudioDto(media, audioEntity.getDuration(), audioEntity.getSampleRate());
    }

    public AudioEntity toEntity(AudioDto audioDto){
        MediaEntity media = super.toEntity(audioDto);
        return new AudioEntity(media, audioDto.getDuration(), audioDto.getSampleRate());
    }
}
