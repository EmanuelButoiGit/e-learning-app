package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.options.FileOption;
import com.emanuel.mediaservice.converters.ImageConverter;
import com.emanuel.mediaservice.converters.MediaConverter;
import com.emanuel.mediaservice.entities.ImageEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.repositories.ImageRepository;
import com.emanuel.starterlibrary.dtos.ImageDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.exceptions.DataBaseException;
import com.emanuel.starterlibrary.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ImageService {
    private final ValidationService validationService;
    private final MediaService mediaService;
    private final MediaConverter mediaConverter;
    private final QualityService qualityService;
    private final ImageRepository imageRepository;
    private final ImageConverter imageConverter;

    @SneakyThrows
    public ImageDto uploadImage(MultipartFile file, String title, String description) {
        String extension = validationService.validateFile(FileOption.getIMAGE_EXTENSIONS(), file);
        MediaDto mediaFields = mediaService.getMediaFields(file, title, description, extension);
        BufferedImage image = null;
        try (InputStream inputStream = file.getInputStream()) {
            image = ImageIO.read(inputStream);
        }  catch (IOException e){
            log.error("Error reading image file: {}", e.getMessage());
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
        mediaService.sendNotification(savedEntity.getTitle());
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
            throw new DataBaseException(MediaService.DB_FETCH_EXCEPTION + e);
        }
    }

    @SneakyThrows
    public ImageDto getImageById(@NotNull @Min(value = 0) Long id) {
        ImageEntity image = new ImageEntity();
        final ImageEntity entity = image;
        image = imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("%s not found with id %s ", entity.getClass(), id));
        return imageConverter.toDto(image);
    }

    public ImageDto deleteImage(@NotNull @Min(value = 0) Long id) {
        ImageDto image = getImageById(id);
        imageRepository.delete(imageConverter.toEntity(image));
        return image;
    }

    @SneakyThrows
    public ImageDto updateImage(@NotNull @Min(value = 0) Long id, ImageDto dto) {
        MediaDto media = mediaService.updateMediaFields(id, dto);
        ImageDto updatedImage = new ImageDto(media, dto.getWidth(), dto.getHeight(), dto.getResolutionQuality());
        ImageEntity imageEntity = imageRepository.save(imageConverter.toEntity(updatedImage));
        return imageConverter.toDto(imageEntity);
    }

    public void deleteAllImages(){
        imageRepository.deleteAll();
    }
}
