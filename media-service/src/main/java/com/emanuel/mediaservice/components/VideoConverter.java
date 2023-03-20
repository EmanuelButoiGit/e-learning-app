package com.emanuel.mediaservice.components;

import com.emanuel.mediaservice.dtos.ImageDto;
import com.emanuel.mediaservice.dtos.VideoDto;
import com.emanuel.mediaservice.entities.ImageEntity;
import com.emanuel.mediaservice.entities.VideoEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VideoConverter extends ImageConverter {
    public VideoDto toDto(VideoEntity videoEntity){
        ImageDto image = super.toDto(videoEntity);
        return new VideoDto(image, videoEntity.getDuration(), videoEntity.getAspectRatio(), videoEntity.getFps());
    }

    public VideoEntity toEntity(VideoDto videoDto){
        ImageEntity image = super.toEntity(videoDto);
        return new VideoEntity(image, videoDto.getDuration(), videoDto.getAspectRatio(), videoDto.getFps());
    }
}
