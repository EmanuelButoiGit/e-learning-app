package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.components.ImageConverter;
import com.emanuel.mediaservice.dtos.ImageDto;
import com.emanuel.mediaservice.entities.ImageEntity;
import com.emanuel.mediaservice.exceptions.DataBaseException;
import com.emanuel.mediaservice.exceptions.InfectedFileException;
import com.emanuel.mediaservice.repositories.ImageRepository;
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

    private final ScanService scanService;
    private final ImageRepository imageRepository;
    private final ImageConverter imageConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @SneakyThrows
    public ImageDto uploadImage(MultipartFile file, String title, String description) {
        boolean isInfected = scanService.scanFileForViruses(file);
        if (isInfected) {
            throw new InfectedFileException("The uploaded file is infected with viruses.");
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e){
            LOGGER.error("Error reading image file: {}", e.getMessage());
        }
        if (image == null){
            throw new NullPointerException("Error image file is null");
        }
        String fileName = file.getOriginalFilename();
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

    private String calculateQuality(Integer width, Integer height) {
        if (width <= 320 && height <= 240) {
            return "LOW";
        } else if (width <= 640 && height <= 480) {
            return "MEDIUM";
        } else {
            return "HIGH";
        }
    }

    @SneakyThrows
    public List<ImageDto> getAllImages() {
        try {
            List<ImageEntity> allMedias = imageRepository.findAll();
            return allMedias.stream()
                    .map(imageConverter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataBaseException("Couldn't fetch data from database: " + e.getMessage());
        }
    }

    public ImageDto getImageById(Long id) {
        ImageEntity image = imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ImageEntity not found with id " + id));
        return imageConverter.toDto(image);
    }

    public ImageDto deleteImage(Long id) {
        ImageDto image = getImageById(id);
        imageRepository.delete(imageConverter.toEntity(image));
        return image;
    }

    public ImageDto updateImage(Long id, ImageDto dto) {
        ImageDto image = getImageById(id);
        dto.setId(image.getId());
        dto.setTitle(image.getTitle());
        dto.setDescription(image.getDescription());
        dto.setUploadDate(image.getUploadDate());
        dto.setMimeType(image.getMimeType());
        dto.setContent(image.getContent());
        dto.setSize(image.getSize());
        dto.setWidth(image.getWidth());
        dto.setHeight(image.getHeight());
        dto.setQuality(image.getQuality());
        ImageEntity imageEntity = imageRepository.save(imageConverter.toEntity(dto));
        return imageConverter.toDto(imageEntity);
    }

    public void deleteAllImages(){
        imageRepository.deleteAll();
    }
}
