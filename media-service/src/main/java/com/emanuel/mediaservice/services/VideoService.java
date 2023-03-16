package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.components.VideoConverter;
import com.emanuel.mediaservice.dtos.VideoDto;
import com.emanuel.mediaservice.entities.VideoEntity;
import com.emanuel.mediaservice.exceptions.InfectedFileException;
import com.emanuel.mediaservice.repositories.VideoRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


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

}
