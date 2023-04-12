package com.emanuel.mediaservice.services;

import com.emanuel.starterlibrary.exceptions.WrongExtensionException;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.jsoup.Jsoup;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
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
        Objects.requireNonNull(file, "File cannot be null");
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        String[] parts = fileName.split("\\.");
        String extension = parts[parts.length - 1];
        if (Arrays.stream(fileFormat).noneMatch(ext -> ext.equalsIgnoreCase(extension))) {
            throw new WrongExtensionException(extension);
        }
        Tika tika = new Tika();
        try (InputStream inputStream = file.getInputStream()) {
            String mimeType = tika.detect(inputStream);
            String contentType = file.getContentType();
            // special cases:
            if("video/x-msvideo".equals(mimeType) && "video/avi".equals(contentType) && "avi".equalsIgnoreCase(extension)){
                return  extension;
            } else if ("application/x-matroska".equals(mimeType) && "video/webm".equals(contentType) && "webm".equalsIgnoreCase(extension)) {
                return  extension;
            } else if ("audio/vnd.wave".equals(mimeType) && "audio/wav".equals(contentType) && "wav".equalsIgnoreCase(extension)) {
                return extension;
            } else if ("application/x-tika-ooxml".equals(mimeType) && "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType) && "docx".equalsIgnoreCase(extension)) {
                return extension;
            } else if (!Objects.equals(mimeType, contentType)){
                throw new WrongExtensionException(contentType, fileName, extension);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extension;
    }

    public String sanitizeString(String string){
        // Sanitize string
        Safelist safelist = Safelist.basic();
        Cleaner cleaner = new Cleaner(safelist);
        return cleaner.clean(Jsoup.parse(string)).text();
    }

}
