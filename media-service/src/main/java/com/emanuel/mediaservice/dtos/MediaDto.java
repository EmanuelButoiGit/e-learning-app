package com.emanuel.mediaservice.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class MediaDto {
    private Long id;
    private String title;
    private String description;
    private String fileName;
    private Date uploadDate;
    private String mimeType;
    private byte[] content;
}
