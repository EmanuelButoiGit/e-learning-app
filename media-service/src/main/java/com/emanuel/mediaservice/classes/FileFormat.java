package com.emanuel.mediaservice.classes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileFormat {
    @Getter
    private static final String[] VIDEO_EXTENSIONS = {"avi", "mov", "mp4", "webm", "wmv"};
    @Getter
    private static final String[] IMAGE_EXTENSIONS = {"gif", "jpg", "png"};
    @Getter
    private static final String[] AUDIO_EXTENSIONS = {"wav", "mp3"};
    @Getter
    private static final String[] DOCUMENT_EXTENSIONS = {"docx", "pdf", "txt"};
    @Getter
    private static final String[] MEDIA_EXTENSIONS = Stream.of(VIDEO_EXTENSIONS, IMAGE_EXTENSIONS, AUDIO_EXTENSIONS, DOCUMENT_EXTENSIONS)
            .flatMap(Stream::of)
            .toArray(String[]::new);
}
