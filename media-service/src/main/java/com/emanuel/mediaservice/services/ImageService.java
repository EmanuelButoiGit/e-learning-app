package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.components.ImageConverter;
import com.emanuel.mediaservice.components.MediaConverter;
import com.emanuel.mediaservice.dtos.ImageDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.ImageEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.exceptions.DataBaseException;
import com.emanuel.mediaservice.exceptions.InfectedFileException;
import com.emanuel.mediaservice.repositories.ImageRepository;
import com.emanuel.mediaservice.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ImageService {

    ScanService scanService;
    ImageRepository imageRepository;
    ImageConverter imageConverter;

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @SneakyThrows
    public ImageDto uploadImage(MultipartFile file, String title, String description) {
        boolean isInfected = scanService.scanFileForViruses(file);
        if (isInfected) {
            throw new InfectedFileException("The uploaded file is infected with viruses.");
        }

        String fileName = file.getOriginalFilename();
        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e){
            logger.error("Error reading image file: {}, {}", fileName, e.getMessage());
        }
        if (image == null){
            throw new NullPointerException("Error image file " + fileName + " is null: ");
        }

        byte[] content = file.getBytes();
        Long size = file.getSize();
        String contentType = file.getContentType();
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Integer width = image.getWidth();
        Integer height = image.getHeight();
        String quality = calculateQuality(width, height);
        ImageEntity imageEntity = new ImageEntity(null, title, description, fileName, date, contentType, content, size, width, height, quality);
        ImageEntity savedEntity = imageRepository.save(imageEntity);
        return imageConverter.toDto(savedEntity);
    }

    private String calculateQuality(Integer width, Integer height){
        if (width <= 320 && height <= 240) {
            return "LOW";
        } else if (width <= 640 && height <= 480) {
            return "MEDIUM";
        } else {
            return "HIGH";
        }
    }
}
