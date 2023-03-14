package com.emanuel.mediaservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SwaggerConstants {
    public static final String MEDIA_CONTENT = "";
    public static final String MEDIA_DEFAULT_VALUES =
            "{\n" +
                    "  \"id\": 0,\n" +
                    "  \"title\": \"Default Title\",\n" +
                    "  \"description\": \"Default Description\",\n" +
                    "  \"fileName\": \"default_filename\",\n" +
                    "  \"uploadDate\": \"1970-01-01T00:00:00Z\",\n" +
                    "  \"mimeType\": \"text/plain\",\n" +
                    "  \"content\": " + MEDIA_CONTENT + "\n" +
                    "}";
}
