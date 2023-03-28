package com.emanuel.ratingservice.services;

import com.emanuel.ratingservice.components.RatingConverter;
import com.emanuel.ratingservice.entities.RatingEntity;
import com.emanuel.ratingservice.repositories.RatingRepository;
import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.dtos.RatingDto;
import com.emanuel.starterlibrary.exceptions.DataBaseException;
import com.emanuel.starterlibrary.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingConverter ratingConverter;

    @SneakyThrows
    public RatingDto addRating(RatingDto rating) {
        // check if the media id is valid
        try {
           new RestTemplate().getForEntity("http://localhost:8080/api/media/" + rating.getMediaId(), MediaDto.class);
        } catch (RestClientException e) {
            throw new EntityNotFoundException("Entity not found with id: " + rating.getMediaId());
        }
        // check if a rating already has been added by the user
        List<RatingDto> allRatings = getAllRatings();
        Optional<RatingDto> matchingRating = allRatings.stream()
                .filter(it -> Objects.equals(it.getMediaId(), rating.getMediaId()))
                .findFirst();
        if (matchingRating.isPresent()) {
            throw new DataBaseException("A rating already exists for the given media id: " + rating.getMediaId());
        }
        RatingEntity ratingEntity =
                new RatingEntity(null,
                        rating.getMediaId(),
                        rating.getTitle(),
                        rating.getDescription(),
                        calculateGeneralRating(rating.getTutorRating(), rating.getContentRating(), rating.getContentStructureRating(),
                                rating.getPresentationRating(), rating.getEngagementRating(), rating.getDifficultyRating()),
                        rating.getTutorRating(),
                        rating.getContentRating(),
                        rating.getContentStructureRating(),
                        rating.getPresentationRating(),
                        rating.getEngagementRating(),
                        rating.getDifficultyRating()
                );
        RatingEntity savedEntity = ratingRepository.save(ratingEntity);
        return ratingConverter.toDto(savedEntity);
    }

    private Float calculateGeneralRating(Float tutorRating, Float contentRating, Float contentStructureRating,
                                         Float presentationRating, Float engagementRating, Float difficultyRating){
        float sumOfRatings = tutorRating + contentRating + contentStructureRating + presentationRating + engagementRating + difficultyRating;
        return sumOfRatings / 6;
    }

    @SneakyThrows
    public List<RatingDto> getAllRatings(){
        try {
            List<RatingEntity> allRatings = ratingRepository.findAll();
            return allRatings.stream()
                    .map(ratingConverter::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataBaseException("Couldn't fetch data from database: " + e.getMessage());
        }
    }

    @SneakyThrows
    public RatingDto getRatingById(Long id) {
        RatingEntity rating = new RatingEntity();
        final RatingEntity entity = rating;
        rating = ratingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("%s not found with id %s ", entity.getClass(), id));
        return ratingConverter.toDto(rating);
    }

    public RatingDto deleteRating(Long id) {
        RatingDto rating = getRatingById(id);
        ratingRepository.delete(ratingConverter.toEntity(rating));
        return rating;
    }

    @SneakyThrows
    public RatingDto updateRating(Long id, RatingDto updatedRating) {
        // check if the media id is valid
        try {
            new RestTemplate().getForEntity("http://localhost:8080/api/media/" + updatedRating.getMediaId(), MediaDto.class);
        } catch (RestClientException e) {
            throw new EntityNotFoundException("Entity not found with id: " + updatedRating.getMediaId());
        }
        // check if a rating already has been added by the user
        List<RatingDto> allRatings = getAllRatings();
        Optional<RatingDto> matchingRating = allRatings.stream()
                .filter(it -> Objects.equals(it.getMediaId(), updatedRating.getMediaId()))
                .findFirst();
        if (matchingRating.isPresent() && !Objects.equals(matchingRating.get().getMediaId(), updatedRating.getMediaId())) {
            throw new DataBaseException("A rating already exists for the given media id: " + updatedRating.getMediaId());
        }
        RatingDto rating = getRatingById(id);
        rating.setId(updatedRating.getId());
        rating.setMediaId(updatedRating.getMediaId());
        rating.setTitle(updatedRating.getTitle());
        rating.setDescription(updatedRating.getDescription());
        Float generalRating = calculateGeneralRating(updatedRating.getTutorRating(), updatedRating.getContentRating(), updatedRating.getContentStructureRating(),
                updatedRating.getPresentationRating(), updatedRating.getEngagementRating(), updatedRating.getDifficultyRating());
        rating.setGeneralRating(generalRating);
        rating.setTutorRating(updatedRating.getTutorRating());
        rating.setContentRating(updatedRating.getContentRating());
        rating.setContentStructureRating(updatedRating.getContentStructureRating());
        rating.setPresentationRating(updatedRating.getPresentationRating());
        rating.setEngagementRating(updatedRating.getEngagementRating());
        rating.setDifficultyRating(updatedRating.getDifficultyRating());
        RatingEntity mediaEntity = ratingRepository.save(ratingConverter.toEntity(rating));
        return ratingConverter.toDto(mediaEntity);
    }

    public void deleteAllRatings(){
        ratingRepository.deleteAll();
    }

    @SneakyThrows
    public RatingDto getMediaByRatingId(Long id) {
        List<RatingDto> allRatings = getAllRatings();
        Optional<RatingDto> rating = allRatings.stream().filter(ratingDto -> ratingDto.getMediaId().equals(id)).findFirst();
        if(!rating.isPresent()){
            throw new NotFoundException("Media with id " + id + " has no rating assigned");
        } else {
            return rating.get();
        }
    }
}
