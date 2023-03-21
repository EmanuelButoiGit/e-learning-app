package com.emanuel.mediaservice.classes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileFormats {
    @Getter
    private static final List<FileFormat> VIDEO_FORMATS = Arrays.asList(
            new FileFormat("avi", "video/avi"),
            new FileFormat("mov", "video/quicktime"),
            new FileFormat("mp4", "video/mp4"),
            new FileFormat("webm", "video/webm"),
            new FileFormat("wmv", "video/x-ms-wmv")
    );

    @Getter
    private static final List<FileFormat> IMAGE_FORMATS = Arrays.asList(
            new FileFormat("gif", "image/gif"),
            new FileFormat("jpg", "image/jpeg"),
            new FileFormat("png", "image/png")
    );

    @Getter
    private static final List<FileFormat> DOCUMENT_FORMATS = Arrays.asList(
            new FileFormat("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
            new FileFormat("pdf", "application/pdf"),
            new FileFormat("txt", "text/plain")
    );

    @Getter
    private static final List<FileFormat> AUDIO_FORMATS = Arrays.asList(
            new FileFormat("wav", "audio/wav"),
            new FileFormat("mp3", "audio/mpeg")
    );

    @Getter
    private static final List<FileFormat> MEDIA_FORMATS = Stream.concat(
            Stream.concat(
                    VIDEO_FORMATS.stream(),
                    IMAGE_FORMATS.stream()
            ),
                    Stream.concat(
                            AUDIO_FORMATS.stream(),
                            DOCUMENT_FORMATS.stream()
                    )
            )
            .collect(Collectors.toList());
}
