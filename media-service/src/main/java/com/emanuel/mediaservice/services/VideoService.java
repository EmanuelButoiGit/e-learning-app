package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.components.VideoConverter;
import com.emanuel.mediaservice.dtos.VideoDto;
import com.emanuel.mediaservice.entities.VideoEntity;
import com.emanuel.mediaservice.exceptions.DataBaseException;
import com.emanuel.mediaservice.exceptions.InfectedFileException;
import com.emanuel.mediaservice.repositories.VideoRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class VideoService {

    private final ScanService scanService;
    private final QualityService qualityService;
    private final VideoRepository videoRepository;
    private final VideoConverter videoConverter;

    @SneakyThrows
    public VideoDto uploadVideo(MultipartFile file, String title, String description) {
        boolean isInfected = scanService.scanFileForViruses(file);
        if (isInfected) {
            throw new InfectedFileException("The uploaded file is infected with viruses.");
        }
        String fileName = file.getOriginalFilename();
        byte[] content = file.getBytes();
        String contentType = file.getContentType();
        Long size = file.getSize();
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

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

        VideoEntity videoEntity = new VideoEntity(null, title, description, fileName, date, contentType, content, size, width, height, qualityResolution, duration, aspectRatio, fps);
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
        boolean isInfected = scanService.scanContentForViruses(dto.getContent(), dto.getFileName());
        if (isInfected) {
            throw new InfectedFileException("The updated content is infected with viruses.");
        }
        VideoDto video = getVideoById(id);
        video.setId(dto.getId());
        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setUploadDate(dto.getUploadDate());
        video.setMimeType(dto.getMimeType());
        video.setContent(dto.getContent());
        video.setSize(dto.getSize());
        video.setWidth(dto.getWidth());
        video.setHeight(dto.getHeight());
        video.setResolutionQuality(dto.getResolutionQuality());
        video.setDuration(dto.getDuration());
        video.setAspectRatio(dto.getAspectRatio());
        video.setFps(dto.getFps());
        VideoEntity videoEntity = videoRepository.save(videoConverter.toEntity(video));
        return videoConverter.toDto(videoEntity);
    }

    public void deleteAllVideos(){
        videoRepository.deleteAll();
    }
}
