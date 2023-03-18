package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.components.AudioConverter;
import com.emanuel.mediaservice.dtos.AudioDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.AudioEntity;
import com.emanuel.mediaservice.exceptions.DataBaseException;
import com.emanuel.mediaservice.exceptions.EntityNotFoundException;
import com.emanuel.mediaservice.exceptions.Mp3Exception;
import com.emanuel.mediaservice.repositories.AudioRepository;
import com.mpatric.mp3agic.Mp3File;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AudioService {

    private final MediaService mediaService;
    private final AudioRepository audioRepository;
    private final AudioConverter audioConverter;

    @SneakyThrows
    public AudioDto uploadAudio(MultipartFile file, String title, String description) {
        MediaDto mediaFields = mediaService.getMediaFields(file);
        String[] parts = mediaFields.getFileName().split("\\.");
        String extension = parts[parts.length - 1];
        float sampleRate = 0;
        long duration = 0;

        if("wav".equals(extension) || "ogg".equals(extension)) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mediaFields.getContent());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            AudioFormat audioFormat = audioInputStream.getFormat();
            sampleRate = audioFormat.getSampleRate();
            float frameRate = audioFormat.getFrameRate();
            duration = (long) (mediaFields.getSize() / (frameRate * audioFormat.getFrameSize()));
        } else if ("mp3".equals(extension)) {
            try {
                Mp3File mp3file = new Mp3File(mediaFields.getFileName());
                sampleRate = mp3file.getSampleRate();
                duration = mp3file.getLengthInSeconds();
            } catch (Exception e) {
                throw new Mp3Exception("Couldn't get mp3 fields: " + e.getMessage());
            }
        }

        AudioEntity audioEntity = AudioEntity.builder()
                .id(null)
                .title(title)
                .description(description)
                .fileName(mediaFields.getFileName())
                .uploadDate(mediaFields.getUploadDate())
                .mimeType(mediaFields.getMimeType())
                .content(mediaFields.getContent())
                .size(mediaFields.getSize())
                .duration(duration)
                .sampleRate(sampleRate)
                .build();
        AudioEntity savedEntity = audioRepository.save(audioEntity);
        return audioConverter.toDto(savedEntity);
    }

    @SneakyThrows
    public List<AudioDto> getAllAudios() {
        try {
            List<AudioEntity> allAudios = audioRepository.findAll();
            return allAudios.stream()
                    .map(audioConverter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataBaseException("Couldn't fetch data from database: " + e.getMessage());
        }
    }

    @SneakyThrows
    public AudioDto getAudioById(Long id) {
        AudioEntity audio = new AudioEntity();
        final AudioEntity entity = audio;
        audio = audioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("%s not found with %s ", entity.getClass(), id));
        return audioConverter.toDto(audio);
    }

    public AudioDto deleteAudio(Long id) {
        AudioDto audio = getAudioById(id);
        audioRepository.delete(audioConverter.toEntity(audio));
        return audio;
    }

    @SneakyThrows
    public AudioDto updateAudio(Long id, AudioDto dto) {
        MediaDto media = mediaService.updateMediaFields(id, dto);
        AudioDto updatedAudio = AudioDto.builder()
                .id(media.getId())
                .title(media.getTitle())
                .description(media.getDescription())
                .fileName(media.getFileName())
                .uploadDate(media.getUploadDate())
                .mimeType(media.getMimeType())
                .content(media.getContent())
                .size(media.getSize())
                .duration(dto.getDuration())
                .sampleRate(dto.getSampleRate())
                .build();
        AudioEntity audioEntity = audioRepository.save(audioConverter.toEntity(updatedAudio));
        return audioConverter.toDto(audioEntity);
    }

    public void deleteAllAudios() {
        audioRepository.deleteAll();
    }
}
