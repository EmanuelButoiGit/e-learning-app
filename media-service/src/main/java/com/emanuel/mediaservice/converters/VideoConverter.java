package com.emanuel.mediaservice.converters;

import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.entities.VideoEntity;
import com.emanuel.starterlibrary.dtos.ImageDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.dtos.VideoDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VideoConverter extends MediaConverter {
    public VideoDto toDto(VideoEntity videoEntity){
        MediaDto media = super.toDto(videoEntity);
        ImageDto image = new ImageDto(media, videoEntity.getWidth(), videoEntity.getHeight(), videoEntity.getResolutionQuality());
        return new VideoDto(image, videoEntity.getDuration(), videoEntity.getAspectRatio(), videoEntity.getFps());
    }

    public VideoEntity toEntity(VideoDto videoDto){
        MediaEntity media = super.toEntity(videoDto);
        return new VideoEntity(media, videoDto.getWidth(), videoDto.getHeight(), videoDto.getResolutionQuality(),
                videoDto.getDuration(), videoDto.getAspectRatio(), videoDto.getFps());
    }
}
