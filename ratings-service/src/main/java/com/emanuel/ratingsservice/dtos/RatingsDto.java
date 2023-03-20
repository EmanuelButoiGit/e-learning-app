package com.emanuel.ratingsservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingsDto {
    private Float generalRating;
    private Float tutorRating;
    private Float contentRating;
    private Float contentStructureRating;
    private Float presentationRating;
    private Float engagementRating;
    private Float difficultyRating;
}
