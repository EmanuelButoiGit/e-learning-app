package com.emanuel.mediaservice.services;

import com.emanuel.mediaservice.exceptions.WrongExtensionException;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.stream.Stream;

public class RestrictionService {

    private static final String[] VIDEO_EXTENSIONS = {"avi", "mov", "mp4", "webm", "wmv"};
    private static final String[] IMAGE_EXTENSIONS = {"gif", "jpg", "png", "webm", "wmv"};
    private static final String[] AUDIO_EXTENSIONS = {"mp3", "wav"};
    private static final String[] DOCUMENT_EXTENSIONS = {"docx", "txt", "pdf"};

    private static final String[] MEDIA_EXTENSIONS = Stream.of(VIDEO_EXTENSIONS, IMAGE_EXTENSIONS, AUDIO_EXTENSIONS, DOCUMENT_EXTENSIONS)
            .flatMap(Stream::of)
            .toArray(String[]::new);

    @SneakyThrows
    public void checkMediaExtensions(String fileName, String mime){
        String[] parts = fileName.split("\\.");
        String extension = parts[parts.length - 1];
        if (Arrays.stream(MEDIA_EXTENSIONS).noneMatch(ext -> ext.equals(extension))) {
            throw new WrongExtensionException("The application does not support ." + extension + " extension");
        }
    }

    @SneakyThrows
    public void checkVideoExtensions(String fileName, String mime){
        String[] parts = fileName.split("\\.");
        String extension = parts[parts.length - 1];
        if (Arrays.stream(VIDEO_EXTENSIONS).noneMatch(ext -> ext.equals(extension))) {
            throw new WrongExtensionException("The application does not support ." + extension + " extension");
        }
        if (extension.equals(VIDEO_EXTENSIONS[0]) && !mime.equals("video/avi")){
            throw new WrongExtensionException(fileName, mime, extension);
        } else if (extension.equals(VIDEO_EXTENSIONS[1]) && !mime.equals("video/quicktime")) {
            throw new WrongExtensionException(fileName, mime, extension);
        } else if (extension.equals(VIDEO_EXTENSIONS[2]) && !mime.equals("video/mp4")) {
            throw new WrongExtensionException(fileName, mime, extension);
        } else if (extension.equals(VIDEO_EXTENSIONS[3]) && !mime.equals("video/webm")) {
            throw new WrongExtensionException(fileName, mime, extension);
        } else if (extension.equals(VIDEO_EXTENSIONS[4]) && !mime.equals("video/x-ms-wmv")) {
            throw new WrongExtensionException(fileName, mime, extension);
        }
    }
}
