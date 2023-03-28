package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.converters.AudioConverter;
import com.emanuel.mediaservice.converters.MediaConverter;
import com.emanuel.mediaservice.entities.AudioEntity;
import com.emanuel.mediaservice.entities.MediaEntity;
import com.emanuel.mediaservice.options.FileOption;
import com.emanuel.mediaservice.repositories.AudioRepository;
import com.emanuel.starterlibrary.dtos.AudioDto;
import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.exceptions.DataBaseException;
import com.emanuel.starterlibrary.exceptions.EntityNotFoundException;
import com.emanuel.starterlibrary.exceptions.Mp3Exception;
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
        String extension = restrictionService.validateExtensionAndMimeType(FileOption.getAUDIO_EXTENSIONS(), file);
        MediaDto mediaFields = mediaService.getMediaFields(file, title, description, extension);
        float sampleRate = 0;
        long duration = 0;
        if("wav".equalsIgnoreCase(extension)) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mediaFields.getContent());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            AudioFormat audioFormat = audioInputStream.getFormat();
            sampleRate = audioFormat.getSampleRate();
            float frameRate = audioFormat.getFrameRate();
            duration = (long) (mediaFields.getSize() / (frameRate * audioFormat.getFrameSize()));
        } else if ("mp3".equalsIgnoreCase(extension)) {
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
