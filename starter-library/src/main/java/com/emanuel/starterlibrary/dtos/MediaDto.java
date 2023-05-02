package com.emanuel.starterlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDto {
    @NotNull(message = "ID cannot be null")
    @Positive(message = "ID must be positive")
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
    private String description;

    @NotBlank
    @Positive
    private String fileName;

    @NotBlank
    @Positive
    private String extension;

    @NotNull
    @Positive
    private Date uploadDate;

    @NotBlank
    @Positive
    private String mimeType;

    @NotNull
    @Positive
    private byte[] content;

    @NotNull
    @Positive
    private Long size;
}
