package com.emanuel.mediaservice.dtos;

import lombok.Data;

import java.awt.*;
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
    // additional fields:
    private Long duration;
    private Dimension resolution;
    private Double fps;
}
