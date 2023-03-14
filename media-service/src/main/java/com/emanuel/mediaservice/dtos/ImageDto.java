package com.emanuel.mediaservice.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ImageDto {
    private Long id;
    private String title;
    private String description;
    private String fileName;
    private Date uploadDate;
    private String mimeType;
    private byte[] content;
    private Long size;
    // additional fields:
    private Integer width;
    private Integer height;
    private String quality;
}
