package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.classes.FileFormats;
import com.emanuel.mediaservice.components.ImageConverter;
import com.emanuel.mediaservice.components.VideoConverter;
import com.emanuel.mediaservice.dtos.ImageDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.dtos.VideoDto;
import com.emanuel.mediaservice.entities.ImageEntity;
import com.emanuel.mediaservice.entities.VideoEntity;
import com.emanuel.mediaservice.exceptions.DataBaseException;
import com.emanuel.mediaservice.repositories.VideoRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class VideoService {

    private final RestrictionService restrictionService;
    private final MediaService mediaService;
    private final ImageConverter imageConverter;
    private final QualityService qualityService;
    private final VideoRepository videoRepository;
    private final VideoConverter videoConverter;

    @SneakyThrows
    public VideoDto uploadVideo(MultipartFile file, String title, String description) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        restrictionService.validateExtensionAndMimeType(FileFormats.getVIDEO_FORMATS(), fileName, file.getContentType());
        MediaDto mediaFields = mediaService.getMediaFields(file, title, description);
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

        ImageDto imageDto = new ImageDto(mediaFields, width, height, qualityResolution);
        ImageEntity entity = imageConverter.toEntity(imageDto);
        VideoEntity videoEntity = new VideoEntity(entity, duration, aspectRatio, fps);
        VideoEntity savedEntity = videoRepository.save(videoEntity);
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
            throw new DataBaseException("Couldn't fetch data from database: " + e.getMessage());
        }
    }

    public VideoDto getVideoById(Long id) {
        VideoEntity video = videoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("VideoEntity not found with id " + id));
        return videoConverter.toDto(video);
    }

    public VideoDto deleteVideo(Long id) {
        VideoDto video = getVideoById(id);
        videoRepository.delete(videoConverter.toEntity(video));
        return video;
    }

    @SneakyThrows
    public VideoDto updateVideo(Long id, VideoDto dto) {
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
