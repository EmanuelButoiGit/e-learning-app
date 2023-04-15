package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.converters.MediaConverter;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.options.FileOption;
import com.emanuel.mediaservice.repositories.MediaRepository;
import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.exceptions.DataBaseException;
import com.emanuel.starterlibrary.exceptions.EntityNotFoundException;
import com.emanuel.starterlibrary.exceptions.InfectedFileException;
import com.emanuel.starterlibrary.services.SanitizationService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MediaService {

    private final ValidationService validationService;
    private final SanitizationService sanitizationService;
    private final ScanService scanService;
    private final MediaRepository mediaRepository;
    private final MediaConverter mediaConverter;

    public MediaDto uploadMedia(MultipartFile file, String title, String description) {
        String extension = validationService.validateFile(FileOption.getMEDIA_EXTENSIONS(), file);
        MediaDto mediaFields = getMediaFields(file, title, description, extension);
        MediaEntity mediaEntity =
                new MediaEntity(null,
                                mediaFields.getTitle(),
                                mediaFields.getDescription(),
                                mediaFields.getFileName(),
                                mediaFields.getExtension(),
                                mediaFields.getUploadDate(),
                                mediaFields.getMimeType(),
                                mediaFields.getContent(),
                                mediaFields.getSize()
                );
        MediaEntity savedEntity = mediaRepository.save(mediaEntity);
        sendNotification(savedEntity.getTitle());
        return mediaConverter.toDto(savedEntity);
    }

    @SneakyThrows
    public void sendNotification(String name) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8083/api/notification/new/media")
                    .queryParam("newMedia", "{name}");
            String url = builder.buildAndExpand(name).toUriString();
            new RestTemplate().postForEntity(url, Void.class, Void.class);
        } catch (RestClientException e) {
            throw new RestClientException("Can't send notification", e);
        }
    }

    @SneakyThrows
    public MediaDto getMediaFields(MultipartFile file, String title, String description, String extension){
        title = sanitizationService.sanitizeString(title);
        description = sanitizationService.sanitizeString(description);
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
        return new MediaDto(null, title, description, fileName, extension, date, contentType, content, size);
    }

    @SneakyThrows
    public List<MediaDto> getAllMedias(){
        try {
            List<MediaEntity> allMedias = mediaRepository.findAll();
            return allMedias.stream()
                    .map(mediaConverter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataBaseException("Couldn't fetch data from database: " + e.getMessage());
        }
    }

    @SneakyThrows
    public MediaDto getMediaById(Long id) {
        MediaEntity media = new MediaEntity();
        final MediaEntity entity = media;
        media = mediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("%s not found with id %s ", entity.getClass(), id));
        return mediaConverter.toDto(media);
    }

    public MediaDto deleteMedia(Long id) {
        MediaDto media = getMediaById(id);
        mediaRepository.delete(mediaConverter.toEntity(media));
        return media;
    }

    public MediaDto updateMedia(Long id, MediaDto dto) {
        MediaDto media = updateMediaFields(id, dto);
        MediaEntity mediaEntity = mediaRepository.save(mediaConverter.toEntity(media));
        return mediaConverter.toDto(mediaEntity);
    }

    @SneakyThrows
    public MediaDto updateMediaFields(Long id, MediaDto dto) {
        dto.setTitle(sanitizationService.sanitizeString(dto.getTitle()));
        dto.setDescription(sanitizationService.sanitizeString(dto.getDescription()));
        boolean isInfected = scanService.scanContentForViruses(dto.getContent(), dto.getFileName());
        if (isInfected) {
            throw new InfectedFileException("The updated content is infected with viruses.");
        }
        MediaDto media = getMediaById(id);
        media.setId(dto.getId());
        media.setTitle(dto.getTitle());
        media.setDescription(dto.getDescription());
        media.setFileName(dto.getFileName());
        media.setExtension(dto.getExtension());
        media.setUploadDate(dto.getUploadDate());
        media.setMimeType(dto.getMimeType());
        media.setContent(dto.getContent());
        media.setSize(dto.getSize());
        return media;
    }

    public void deleteAllMedias(){
        mediaRepository.deleteAll();
    }
}
