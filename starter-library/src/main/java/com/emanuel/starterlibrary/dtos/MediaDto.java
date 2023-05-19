package com.emanuel.starterlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDto implements Serializable {
    @NotNull(message = "ID cannot be null")
    @Min(value = 0, message = "ID must be non-negative")
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
    private Date uploadDate;

    @NotBlank
    @NotEmpty
    private String mimeType;

    @NotNull
    private byte[] content;

    @NotNull
    @Min(value = 0)
    private Long size;
}
