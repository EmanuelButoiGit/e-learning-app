package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.exceptions.WrongExtensionException;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

@Service
public class RestrictionService {

    @SneakyThrows
    public String validateExtensionAndMimeType(String[] fileFormat, MultipartFile file){
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        String[] parts = fileName.split("\\.");
        String extension = parts[parts.length - 1];
        if (Arrays.stream(fileFormat).noneMatch(ext -> ext.equals(extension))) {
            throw new WrongExtensionException(extension);
        }
        Tika tika = new Tika();
        try (InputStream inputStream = file.getInputStream()) {
            String mimeType = tika.detect(inputStream);
            if (!Objects.equals(mimeType, file.getContentType())){
                throw new WrongExtensionException(fileName, file.getContentType(), extension);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extension;
    }
}
