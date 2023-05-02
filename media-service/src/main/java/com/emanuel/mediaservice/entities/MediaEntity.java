package com.emanuel.mediaservice.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
@Table(name = "media")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class MediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
    private String description;

    @NotBlank
    @NotEmpty
    private String fileName;

    @NotBlank
    @NotEmpty
    private String extension;

    @NotNull
    @NotEmpty
    private Date uploadDate;

    @NotBlank
    @NotEmpty
    private String mimeType;

    @Lob
    @NotNull
    private byte[] content;

    @NotNull
    @Min(value = 0) 
    private Long size;
}
