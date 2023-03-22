package com.emanuel.ratingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rating")
@NoArgsConstructor
@AllArgsConstructor
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
