package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.components.MediaConverter;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.exceptions.InfectedFileException;
import com.emanuel.mediaservice.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
@AllArgsConstructor
public class UploadService {

    ScanService scanService;
    MediaRepository mediaRepository;
    MediaConverter mediaConverter;

    @SneakyThrows
    public MediaDto saveFile(MultipartFile file, String title, String description) {
        boolean isInfected = scanService.scanFileForViruses(file);
        if (isInfected) {
            throw new InfectedFileException("The uploaded file is infected with viruses.");
        }
        String fileName = file.getOriginalFilename();
        byte[] content = file.getBytes();
        String contentType = file.getContentType();
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        MediaEntity mediaEntity = new MediaEntity(null, title, description, fileName, date, contentType, content);
        MediaEntity savedEntity = mediaRepository.save(mediaEntity);
        return mediaConverter.toDto(savedEntity);
    }
}
