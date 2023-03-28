package com.emanuel.ratingservice.components;

import com.emanuel.ratingservice.entities.RatingEntity;
import com.emanuel.starterlibrary.dtos.RatingDto;
import org.springframework.stereotype.Component;

@Component
public class RatingConverter {
    public RatingDto toDto(RatingEntity ratingEntity){
        RatingDto dto = new RatingDto();
        dto.setId(ratingEntity.getId());
        dto.setMediaId(ratingEntity.getMediaId());
        dto.setGeneralRating(ratingEntity.getGeneralRating());
        dto.setTitle(ratingEntity.getTitle());
        dto.setDescription(ratingEntity.getDescription());
        dto.setTutorRating(ratingEntity.getTutorRating());
        dto.setContentRating(ratingEntity.getContentRating());
        dto.setContentStructureRating(ratingEntity.getContentStructureRating());
        dto.setPresentationRating(ratingEntity.getPresentationRating());
        dto.setEngagementRating(ratingEntity.getEngagementRating());
        dto.setDifficultyRating(ratingEntity.getDifficultyRating());
        return dto;
    }

    public RatingEntity toEntity(RatingDto ratingDto){
        RatingEntity entity = new RatingEntity();
        entity.setId(ratingDto.getId());
        entity.setMediaId(ratingDto.getMediaId());
        entity.setGeneralRating(ratingDto.getGeneralRating());
        entity.setTitle(ratingDto.getTitle());
        entity.setDescription(ratingDto.getDescription());
        entity.setTutorRating(ratingDto.getTutorRating());
        entity.setContentRating(ratingDto.getContentRating());
        entity.setContentStructureRating(ratingDto.getContentStructureRating());
        entity.setPresentationRating(ratingDto.getPresentationRating());
        entity.setEngagementRating(ratingDto.getEngagementRating());
        entity.setDifficultyRating(ratingDto.getDifficultyRating());
        return entity;
    }
}
