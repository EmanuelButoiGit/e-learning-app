package com.emanuel.recommendationservice.proxies;

import com.emanuel.starterlibrary.dtos.RatingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;


@FeignClient(name = "rating-service")
public interface RatingServiceProxy {
    @GetMapping("api/rating/media/{mediaId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific media rating based on a giving rated media id")
    @ApiResponse(responseCode = "200", description = "Specific media rating retrieved based on a giving rated media id")
    public RatingDto getMediaByRatingId(@PathVariable Long mediaId);
}
