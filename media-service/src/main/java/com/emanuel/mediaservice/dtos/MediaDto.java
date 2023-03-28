package com.emanuel.mediaservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDto {
    private Long id;
    private String title;
    private String description;
    private String fileName;
    private String extension;
    private Date uploadDate;
    private String mimeType;
    private byte[] content;
    private Long size;
}
