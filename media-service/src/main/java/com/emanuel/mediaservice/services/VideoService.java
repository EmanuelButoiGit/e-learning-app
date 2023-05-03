package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.options.FileOption;
import com.emanuel.mediaservice.converters.MediaConverter;
import com.emanuel.mediaservice.converters.VideoConverter;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.entities.VideoEntity;
import com.emanuel.mediaservice.repositories.VideoRepository;
import com.emanuel.starterlibrary.dtos.ImageDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.dtos.VideoDto;
import com.emanuel.starterlibrary.exceptions.DataBaseException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class VideoService {

    private final ValidationService validationService;
    private final MediaService mediaService;
    private final MediaConverter mediaConverter;
    private final QualityService qualityService;
    private final VideoRepository videoRepository;
    private final VideoConverter videoConverter;

    @SneakyThrows
    public VideoDto uploadVideo(MultipartFile file, String title, String description) {
        String extension = validationService.validateFile(FileOption.getVIDEO_EXTENSIONS(), file);
        MediaDto mediaFields = mediaService.getMediaFields(file, title, description, extension);
        long duration;
        int width;
        int height;
        int qualityResolution;
        double aspectRatio;
        double fps;
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(file.getInputStream())) {
            grabber.start();
            duration = grabber.getLengthInTime() / 1000000; //s
            width = grabber.getImageWidth();
            height = grabber.getImageHeight();
            qualityResolution = qualityService.calculateResolutionQuality(width, height);
            aspectRatio = grabber.getAspectRatio();
            fps = grabber.getVideoFrameRate();
        }

        MediaEntity entity = mediaConverter.toEntity(mediaFields);
        VideoEntity videoEntity = new VideoEntity(entity, width, height, qualityResolution, duration, aspectRatio, fps);
        VideoEntity savedEntity = videoRepository.save(videoEntity);
        mediaService.sendNotification(savedEntity.getTitle());
        return videoConverter.toDto(savedEntity);
    }

    @SneakyThrows
    public List<VideoDto> getAllVideos() {
        try {
            List<VideoEntity> allVideos = videoRepository.findAll();
            return allVideos.stream()
                    .map(videoConverter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataBaseException(MediaService.DB_FETCH_EXCEPTION + e);
        }
    }

    public VideoDto getVideoById(@NotNull @Min(value = 0) Long id) {
        VideoEntity video = videoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("VideoEntity not found with id " + id));
        return videoConverter.toDto(video);
    }

    public VideoDto deleteVideo(@NotNull @Min(value = 0) Long id) {
        VideoDto video = getVideoById(id);
        videoRepository.delete(videoConverter.toEntity(video));
        return video;
    }

    @SneakyThrows
    public VideoDto updateVideo(@NotNull @Min(value = 0) Long id, VideoDto dto) {
        MediaDto media = mediaService.updateMediaFields(id, dto);
        ImageDto image = new ImageDto(media, dto.getWidth(), dto.getHeight(), dto.getResolutionQuality());
        VideoDto updatedVideo = new VideoDto(image, dto.getDuration(), dto.getAspectRatio(), dto.getFps());
        VideoEntity videoEntity = videoRepository.save(videoConverter.toEntity(updatedVideo));
        return videoConverter.toDto(videoEntity);
    }

    public void deleteAllVideos(){
        videoRepository.deleteAll();
    }
}
