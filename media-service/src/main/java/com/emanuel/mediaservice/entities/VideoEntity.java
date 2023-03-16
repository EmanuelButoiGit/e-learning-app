package com.emanuel.mediaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "video")
@NoArgsConstructor
@AllArgsConstructor
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String fileName;
    private Date uploadDate;
    private String mimeType;
    @Lob
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
