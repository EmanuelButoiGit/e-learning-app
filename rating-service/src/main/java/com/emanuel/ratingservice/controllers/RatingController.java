package com.emanuel.ratingservice.controllers;

import com.emanuel.ratingservice.services.RatingService;
import com.emanuel.starterlibrary.dtos.RatingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/rating")
public class RatingController {
    private final RatingService ratingService;

    @SneakyThrows
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a rating to a media file")
    @ApiResponse(responseCode = "201", description = "Rating added to media")
    public RatingDto addRating(@RequestBody() RatingDto rating)
    {
        return ratingService.addRating(rating);
    }

    @SneakyThrows
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all media ratings")
    @ApiResponse(responseCode = "200", description = "All media ratings retrieved")
    public List<RatingDto> getAllRatings()
    {
        return ratingService.getAllRatings();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific media rating based on a giving media rating id")
    @ApiResponse(responseCode = "200", description = "Specific media rating retrieved based on a giving media rating id")
    public RatingDto getRatingById(@PathVariable Long id)
    {
        return ratingService.getRatingById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a specific media rating based on a giving media rating id")
    @ApiResponse(responseCode = "200", description = "Specific media rating was deleted based on a giving media rating id")
    public RatingDto deleteRating(@PathVariable Long id)
    {
        return ratingService.deleteRating(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a specific media rating file based on a giving media rating id")
    @ApiResponse(responseCode = "200", description = "Specific media rating file was updated based on a giving media rating id")
    public RatingDto updateRating(@PathVariable("id") Long id, @RequestBody @Valid RatingDto rating) {
        return ratingService.updateRating(id, rating);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all ratings")
    @ApiResponse(responseCode = "204", description = "All ratings were deleted")
    public void deleteAllRatings(){
        ratingService.deleteAllRatings();
    }

    @GetMapping("media/{mediaId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific media rating based on a giving rated media id")
    @ApiResponse(responseCode = "200", description = "Specific media rating retrieved based on a giving rated media id")
    public RatingDto getMediaByRatingId(@PathVariable Long mediaId)
    {
        return ratingService.getMediaByRatingId(mediaId);
    }
}