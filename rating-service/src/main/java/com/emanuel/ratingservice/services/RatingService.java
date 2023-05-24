package com.emanuel.ratingservice.services;

import com.emanuel.ratingservice.components.RatingConverter;
import com.emanuel.ratingservice.entities.RatingEntity;
import com.emanuel.ratingservice.proxies.MediaServiceProxy;
import com.emanuel.ratingservice.repositories.RatingRepository;
import com.emanuel.starterlibrary.dtos.MediaDto;
import com.emanuel.starterlibrary.dtos.RatingDto;
import com.emanuel.starterlibrary.exceptions.DataBaseException;
import com.emanuel.starterlibrary.exceptions.EntityNotFoundException;
import com.emanuel.starterlibrary.services.SanitizationService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RatingService {

    private final MediaServiceProxy mediaServiceProxy;
    private final RatingRepository ratingRepository;
    private final SanitizationService sanitizationService;
    private final RatingConverter ratingConverter;

    @SneakyThrows
    public RatingDto addRating(@Valid RatingDto rating) {
        checkIfMediaIdIsValid(rating);
        List<RatingDto> allRatings = getAllRatings();
        checkIfRatingHasAlreadyBeenAddedByTheUser(rating, allRatings);
        rating.setTitle(sanitizationService.sanitizeString(rating.getTitle()));
        rating.setDescription(sanitizationService.sanitizeString(rating.getDescription()));
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

    private void checkIfRatingHasAlreadyBeenAddedByTheUser(RatingDto rating, List<RatingDto> allRatings) throws DataBaseException {
        Optional<RatingDto> matchingRating = allRatings.stream()
                .filter(it -> Objects.equals(it.getMediaId(), rating.getMediaId()))
                .findFirst();
        if (matchingRating.isPresent()) {
            throw new DataBaseException("A rating already exists for the given media id: " + rating.getMediaId());
        }
    }

    @SneakyThrows
    private void checkIfMediaIdIsValid(RatingDto rating){
        try {
            mediaServiceProxy.getMediaById(rating.getMediaId());
        } catch (Exception e) {
            throw new EntityNotFoundException(rating.getMediaId());
        }
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
                    .toList();
        } catch (Exception e) {
            throw new DataBaseException("Couldn't fetch data from database: " + e.getMessage());
        }
    }

    @SneakyThrows
    public RatingDto getRatingById(@NotNull @Min(value = 0) Long id) {
        RatingEntity rating = new RatingEntity();
        final RatingEntity entity = rating;
        rating = ratingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("%s not found with id %s ", entity.getClass(), id));
        return ratingConverter.toDto(rating);
    }

    public RatingDto deleteRating(@NotNull @Min(value = 0) Long id) {
        RatingDto rating = getRatingById(id);
        ratingRepository.delete(ratingConverter.toEntity(rating));
        return rating;
    }

    @SneakyThrows
    public RatingDto updateRating(@NotNull @Min(value = 0) Long id, @Valid RatingDto updatedRating) {
        checkIfMediaIdIsValid(updatedRating);
        List<RatingDto> allRatings = getAllRatings();
        RatingDto rating = getRatingById(id);
        if(!Objects.equals(rating.getMediaId(), updatedRating.getMediaId())) {
            checkIfRatingHasAlreadyBeenAddedByTheUser(updatedRating, allRatings);
        }
        updatedRating.setTitle(sanitizationService.sanitizeString(rating.getTitle()));
        updatedRating.setDescription(sanitizationService.sanitizeString(rating.getDescription()));
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
    public RatingDto getMediaByRatingId(@NotNull @Min(value = 0) Long id) {
        List<RatingDto> allRatings = getAllRatings();
        Optional<RatingDto> rating = allRatings.stream().filter(ratingDto -> ratingDto.getMediaId().equals(id)).findFirst();
        if(!rating.isPresent()){
            throw new NotFoundException("Media with id " + id + " has no rating assigned");
        } else {
            return rating.get();
        }
    }
}
