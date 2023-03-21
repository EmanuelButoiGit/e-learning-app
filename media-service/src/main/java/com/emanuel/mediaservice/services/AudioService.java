package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.classes.FileFormat;
import com.emanuel.mediaservice.components.AudioConverter;
import com.emanuel.mediaservice.components.MediaConverter;
import com.emanuel.mediaservice.dtos.AudioDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.entities.AudioEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.exceptions.DataBaseException;
import com.emanuel.mediaservice.exceptions.EntityNotFoundException;
import com.emanuel.mediaservice.exceptions.Mp3Exception;
import com.emanuel.mediaservice.repositories.AudioRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AudioService {

    private final RestrictionService restrictionService;
    private final MediaService mediaService;
    private final MediaConverter mediaConverter;
    private final AudioRepository audioRepository;
    private final AudioConverter audioConverter;

    @SneakyThrows
    public AudioDto uploadAudio(MultipartFile file, String title, String description) {
        String extension = restrictionService.validateExtensionAndMimeType(FileFormat.getAUDIO_EXTENSIONS(), file);
        MediaDto mediaFields = mediaService.getMediaFields(file, title, description);
        float sampleRate = 0;
        long duration = 0;
        if("wav".equals(extension)) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mediaFields.getContent());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            AudioFormat audioFormat = audioInputStream.getFormat();
            sampleRate = audioFormat.getSampleRate();
            float frameRate = audioFormat.getFrameRate();
            duration = (long) (mediaFields.getSize() / (frameRate * audioFormat.getFrameSize()));
        } else if ("mp3".equals(extension)) {
            try {
                File tempFile = File.createTempFile("temp", ".mp3");
                file.transferTo(tempFile);
                AudioFile audioFile = AudioFileIO.read(tempFile);
                sampleRate = audioFile.getAudioHeader().getSampleRateAsNumber();
                duration = audioFile.getAudioHeader().getTrackLength();
            } catch (Exception e) {
                throw new Mp3Exception("Couldn't get mp3 fields: " + e.getMessage());
            }
        }

        MediaEntity entity = mediaConverter.toEntity(mediaFields);
        AudioEntity audioEntity = new AudioEntity(entity, duration, sampleRate);
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
        audio = audioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("%s not found with id %s ", entity.getClass(), id));
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
        AudioDto updatedAudio = new AudioDto(media, dto.getDuration(), dto.getSampleRate());
        AudioEntity audioEntity = audioRepository.save(audioConverter.toEntity(updatedAudio));
        return audioConverter.toDto(audioEntity);
    }

    public void deleteAllAudios() {
        audioRepository.deleteAll();
    }
}
