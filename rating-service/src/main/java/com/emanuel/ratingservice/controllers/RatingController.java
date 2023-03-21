package com.emanuel.ratingservice.controllers;

import com.emanuel.ratingservice.dtos.RatingDto;
import com.emanuel.ratingservice.services.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}