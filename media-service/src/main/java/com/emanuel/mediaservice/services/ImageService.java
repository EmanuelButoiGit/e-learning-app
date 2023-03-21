package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.classes.FileFormats;
import com.emanuel.mediaservice.components.ImageConverter;
import com.emanuel.mediaservice.components.MediaConverter;
import com.emanuel.mediaservice.dtos.ImageDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.ImageEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.exceptions.DataBaseException;
import com.emanuel.mediaservice.exceptions.EntityNotFoundException;
import com.emanuel.mediaservice.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ImageService {

    private final RestrictionService restrictionService;
    private final MediaService mediaService;
    private final MediaConverter mediaConverter;
    private final QualityService qualityService;
    private final ImageRepository imageRepository;
    private final ImageConverter imageConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @SneakyThrows
    public ImageDto uploadImage(MultipartFile file, String title, String description) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        restrictionService.validateExtensionAndMimeType(FileFormats.getIMAGE_FORMATS(), fileName, file.getContentType());
        MediaDto mediaFields = mediaService.getMediaFields(file, title, description);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e){
            LOGGER.error("Error reading image file: {}", e.getMessage());
        }
        if (image == null){
            throw new NullPointerException("Error image file is null");
        }
        Integer width = image.getWidth();
        Integer height = image.getHeight();
        Integer resolutionQuality = qualityService.calculateResolutionQuality(width, height);

        MediaEntity entity = mediaConverter.toEntity(mediaFields);
        ImageEntity imageEntity = new ImageEntity(entity, width, height, resolutionQuality);
        ImageEntity savedEntity = imageRepository.save(imageEntity);
        return imageConverter.toDto(savedEntity);
    }

    @SneakyThrows
    public List<ImageDto> getAllImages() {
        try {
            List<ImageEntity> allImages = imageRepository.findAll();
            return allImages.stream()
                    .map(imageConverter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataBaseException("Couldn't fetch data from database: " + e.getMessage());
        }
    }

    @SneakyThrows
    public ImageDto getImageById(Long id) {
        ImageEntity image = new ImageEntity();
        final ImageEntity entity = image;
        image = imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("%s not found with id %s ", entity.getClass(), id));
        return imageConverter.toDto(image);
    }

    public ImageDto deleteImage(Long id) {
        ImageDto image = getImageById(id);
        imageRepository.delete(imageConverter.toEntity(image));
        return image;
    }

    @SneakyThrows
    public ImageDto updateImage(Long id, ImageDto dto) {
        MediaDto media = mediaService.updateMediaFields(id, dto);
        ImageDto updatedImage = new ImageDto(media, dto.getWidth(), dto.getHeight(), dto.getResolutionQuality());
        ImageEntity imageEntity = imageRepository.save(imageConverter.toEntity(updatedImage));
        return imageConverter.toDto(imageEntity);
    }

    public void deleteAllImages(){
        imageRepository.deleteAll();
    }
}
