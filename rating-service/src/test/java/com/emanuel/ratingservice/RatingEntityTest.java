package com.emanuel.ratingservice;

import com.emanuel.ratingservice.entities.RatingEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RatingEntityTest {

    private RatingEntity ratingEntity;

    @BeforeEach
    void setUp() {
        ratingEntity = new RatingEntity();
    }

    @Test
    void setId() {
        ratingEntity.setId(1L);
        Assertions.assertEquals(1L, ratingEntity.getId());
    }

    @Test
    void setMediaId() {
        ratingEntity.setMediaId(1L);
        Assertions.assertEquals(1L, ratingEntity.getMediaId());
    }

    @Test
    void setTitle() {
        String title = "Sample Title";
        ratingEntity.setTitle(title);
        Assertions.assertEquals(title, ratingEntity.getTitle());
    }

    @Test
    void setDescription() {
        String description = "Sample Description";
        ratingEntity.setDescription(description);
        Assertions.assertEquals(description, ratingEntity.getDescription());
    }

    @Test
    void setGeneralRating() {
        float generalRating = 4.5f;
        ratingEntity.setGeneralRating(generalRating);
        Assertions.assertEquals(generalRating, ratingEntity.getGeneralRating());
    }

    @Test
    void setTutorRating() {
        float tutorRating = 8.2f;
        ratingEntity.setTutorRating(tutorRating);
        Assertions.assertEquals(tutorRating, ratingEntity.getTutorRating());
    }

    @Test
    void setContentRating() {
        float contentRating = 7.5f;
        ratingEntity.setContentRating(contentRating);
        Assertions.assertEquals(contentRating, ratingEntity.getContentRating());
    }

    @Test
    void setContentStructureRating() {
        float contentStructureRating = 6.0f;
        ratingEntity.setContentStructureRating(contentStructureRating);
        Assertions.assertEquals(contentStructureRating, ratingEntity.getContentStructureRating());
    }

    @Test
    void setPresentationRating() {
        float presentationRating = 9.2f;
        ratingEntity.setPresentationRating(presentationRating);
        Assertions.assertEquals(presentationRating, ratingEntity.getPresentationRating());
    }

    @Test
    void setEngagementRating() {
        float engagementRating = 7.8f;
        ratingEntity.setEngagementRating(engagementRating);
        Assertions.assertEquals(engagementRating, ratingEntity.getEngagementRating());
    }

    @Test
    void setDifficultyRating() {
        float difficultyRating = 5.5f;
        ratingEntity.setDifficultyRating(difficultyRating);
        Assertions.assertEquals(difficultyRating, ratingEntity.getDifficultyRating());
    }
}
