package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.components.VideoConverter;
import com.emanuel.mediaservice.dtos.VideoDto;
import com.emanuel.mediaservice.entities.VideoEntity;
import com.emanuel.mediaservice.exceptions.InfectedFileException;
import com.emanuel.mediaservice.repositories.VideoRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
@AllArgsConstructor
public class VideoService {

    private final ScanService scanService;
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
        Long duration = getVideoDuration(file);
        Dimension resolution = new Dimension(1,1); // getVideoResolution(file);
        Double fps = 0.0; // getVideoFps(file);
        VideoEntity videoEntity = new VideoEntity(null, title, description, fileName, date, contentType, content, size, duration, resolution, fps);
        VideoEntity savedEntity = videoRepository.save(videoEntity);
        return videoConverter.toDto(savedEntity);
    }

    private Long getVideoDuration(MultipartFile file) {
        // TO DO: https://www.vdocipher.com/blog/video-resolution/
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(file.getInputStream())) {
            grabber.start();
            int width = grabber.getImageWidth();
            int height = grabber.getImageHeight();
            long durationMs = grabber.getLengthInTime() / 1000000; // convert to milliseconds
            long durationMin = durationMs / 1000 / 60; // convert to minutes
            return durationMin;
        } catch (FrameGrabber.Exception e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
