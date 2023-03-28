package com.emanuel.starterlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private Long id;
    private Long mediaId;
    private String title;
    private String description;
    private Float generalRating;
    private Float tutorRating;
    private Float contentRating;
    private Float contentStructureRating;
    private Float presentationRating;
    private Float engagementRating;
    private Float difficultyRating;
}
