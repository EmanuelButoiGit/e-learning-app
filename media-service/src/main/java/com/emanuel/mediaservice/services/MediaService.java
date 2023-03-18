package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.components.MediaConverter;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.exceptions.DataBaseException;
import com.emanuel.mediaservice.exceptions.InfectedFileException;
import com.emanuel.mediaservice.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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
public class MediaService {

    private final ScanService scanService;
    private final MediaRepository mediaRepository;
    private final MediaConverter mediaConverter;

    @SneakyThrows
    public MediaDto uploadMedia(MultipartFile file, String title, String description) {
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
        MediaEntity mediaEntity = new MediaEntity(null, title, description, fileName, date, contentType, content, size);
        MediaEntity savedEntity = mediaRepository.save(mediaEntity);
        return mediaConverter.toDto(savedEntity);
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

    public MediaDto getMediaById(Long id) {
        MediaEntity media = mediaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("MediaEntity not found with id " + id));
        return mediaConverter.toDto(media);
    }

    public MediaDto deleteMedia(Long id) {
        MediaDto media = getMediaById(id);
        mediaRepository.delete(mediaConverter.toEntity(media));
        return media;
    }

    @SneakyThrows
    public MediaDto updateMedia(Long id, MediaDto dto) {
        boolean isInfected = scanService.scanContentForViruses(dto.getContent(), dto.getFileName());
        if (isInfected) {
            throw new InfectedFileException("The updated content is infected with viruses.");
        }
        MediaDto media = getMediaById(id);
        media.setId(dto.getId());
        media.setTitle(dto.getTitle());
        media.setDescription(dto.getDescription());
        media.setUploadDate(dto.getUploadDate());
        media.setMimeType(dto.getMimeType());
        media.setContent(dto.getContent());
        media.setSize(dto.getSize());
        MediaEntity mediaEntity = mediaRepository.save(mediaConverter.toEntity(media));
        return mediaConverter.toDto(mediaEntity);
    }

    public void deleteAllMedias(){
        mediaRepository.deleteAll();
    }
}
