package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.components.AudioConverter;
import com.emanuel.mediaservice.dtos.AudioDto;
import com.emanuel.mediaservice.entities.AudioEntity;
import com.emanuel.mediaservice.exceptions.InfectedFileException;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
@AllArgsConstructor
public class AudioService {

    private final ScanService scanService;
    private final AudioRepository audioRepository;
    private final AudioConverter audioConverter;

    @SneakyThrows
    public AudioDto uploadAudio(MultipartFile file, String title, String description) {
        boolean isInfected = scanService.scanFileForViruses(file);
        if (isInfected) {
            throw new InfectedFileException("The uploaded file is infected with viruses.");
        }
        String fileName = file.getOriginalFilename();
        byte[] content = file.getBytes();
        String contentType = file.getContentType();
        long size = file.getSize();
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        assert fileName != null;
        String[] parts = fileName.split("\\.");
        String extension = parts[parts.length - 1];
        float sampleRate = 0;
        long duration = 0;

        if("wav".equals(extension)) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            AudioFormat audioFormat = audioInputStream.getFormat();
            sampleRate = audioFormat.getSampleRate();
            float frameRate = audioFormat.getFrameRate();
            duration = (long) (size / (frameRate * audioFormat.getFrameSize()));
        } else if ("mp3".equals(extension)) {
            try {
                Mp3File mp3file = new Mp3File(fileName);
                sampleRate = mp3file.getSampleRate();
                duration = mp3file.getLengthInSeconds();
            } catch (Exception e) {
                throw new Mp3Exception("Couldn't get mp3 fields: " + e.getMessage());
            }
        }

        AudioEntity audioEntity = new AudioEntity(null, title, description, fileName, date, contentType, content, size, duration, sampleRate);
        AudioEntity savedEntity = audioRepository.save(audioEntity);
        return audioConverter.toDto(savedEntity);
    }
}
