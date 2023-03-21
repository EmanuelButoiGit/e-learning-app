package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.classes.FileFormat;
import com.emanuel.mediaservice.exceptions.WrongExtensionException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestrictionService {
    @SneakyThrows
    public String validateExtensionAndMimeType(List<FileFormat> fileFormatList, String fileName, String mime){
        String[] parts = fileName.split("\\.");
        String extension = parts[parts.length - 1];
        Optional<FileFormat> fileFormat = fileFormatList.stream()
                .filter(f -> f.getExtension().equals(extension))
                .findFirst();
        if (fileFormat.isPresent()) {
            if (!fileFormat.get().getMimeType().equals(mime)) {
                throw new WrongExtensionException(fileName, mime, extension);
            }
        } else {
            throw new WrongExtensionException(extension);
        }
        return extension;
    }
}
