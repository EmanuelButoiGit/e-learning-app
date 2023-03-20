package com.emanuel.ratingsservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rating")
@NoArgsConstructor
@AllArgsConstructor
public class RatingsEntity {
    private Float generalRating;
    private Float tutorRating;
    private Float contentRating;
    private Float contentStructureRating;
    private Float presentationRating;
    private Float engagementRating;
    private Float difficultyRating;
}
