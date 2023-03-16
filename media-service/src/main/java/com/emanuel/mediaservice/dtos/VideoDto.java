package com.emanuel.mediaservice.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class VideoDto {
    private Long id;
    private String title;
    private String description;
    private String fileName;
    private Date uploadDate;
    private String mimeType;
    private byte[] content;
    private Long size;
    // image fields:
    private Integer width;
    private Integer height;
    private Integer resolutionQuality;
    // additional fields:
    private Long duration;
    private Double aspectRatio;
    private Double fps;
}
