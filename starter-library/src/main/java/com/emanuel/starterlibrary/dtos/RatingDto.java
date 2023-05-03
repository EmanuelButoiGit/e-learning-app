package com.emanuel.starterlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    @NotNull(message = "ID cannot be null")
    @Min(value = 0, message = "ID must be non-negative")
    private Long id;

    @NotNull(message = "Media ID cannot be null")
    @Min(value = 0, message = "Media ID must be non-negative")
    private Long mediaId;

    @NotBlank(message = "Title cannot be blank")
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
    private String description;

    @NotNull(message = "General rating cannot be null")
    private Float generalRating;

    @NotNull(message = "Tutor rating cannot be null")
    @DecimalMin(value = "0.0", message = "Tutor rating must be greater than or equal to 0")
    @DecimalMax(value = "10.0", message = "Tutor rating must be less than or equal to 10")
    private Float tutorRating;

    @NotNull(message = "Content rating cannot be null")
    @DecimalMin(value = "0.0", message = "Content rating must be greater than or equal to 0")
    @DecimalMax(value = "10.0", message = "Content rating must be less than or equal to 10")
    private Float contentRating;

    @NotNull(message = "Content structure rating cannot be null")
    @DecimalMin(value = "0.0", message = "Content structure rating must be greater than or equal to 0")
    @DecimalMax(value = "10.0", message = "Content structure rating must be less than or equal to 10")
    private Float contentStructureRating;

    @NotNull(message = "Presentation rating cannot be null")
    @DecimalMin(value = "0.0", message = "Presentation rating must be greater than or equal to 0")
    @DecimalMax(value = "10.0", message = "Presentation rating must be less than or equal to 10")
    private Float presentationRating;

    @NotNull(message = "Engagement rating cannot be null")
    @DecimalMin(value = "0.0", message = "Engagement rating must be greater than or equal to 0")
    @DecimalMax(value = "10.0", message = "Engagement rating must be less than or equal to 10")
    private Float engagementRating;

    @NotNull(message = "Difficulty rating cannot be null")
    @DecimalMin(value = "0.0", message = "Difficulty rating must be greater than or equal to 0")
    @DecimalMax(value = "10.0", message = "Difficulty rating must be less than or equal to 10")
    private Float difficultyRating;
}
