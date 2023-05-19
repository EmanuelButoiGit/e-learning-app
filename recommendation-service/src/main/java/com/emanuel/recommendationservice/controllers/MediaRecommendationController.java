package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.services.MediaRecommendationService;
import com.emanuel.starterlibrary.annotations.Resilient;
import com.emanuel.starterlibrary.dtos.MediaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Resilient
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/recommendation/media")
public class MediaRecommendationController {
    private final MediaRecommendationService mediaRecommendationService;

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all recommended media files")
    @ApiResponse(responseCode = "200", description = "All recommended media retrieved")
    @Cacheable(value = "recommendedMedias", key = "#numberOfMedias", cacheManager = "cacheManager")
    public List<MediaDto> getRecommendedMedia(@RequestParam @NotNull @Min(value = 1) int numberOfMedias)
    {
        return mediaRecommendationService.getRecommendedMedia(numberOfMedias);
    }

    @SneakyThrows
    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get random recommended media file")
    @ApiResponse(responseCode = "200", description = "Random media retrieved")
    @Cacheable(value = "randomRecommendedMedia", cacheManager = "cacheManager")
    public MediaDto getRandomRecommendedMedia()
    {
        return mediaRecommendationService.getRandomRecommendedMedia();
    }

    @SneakyThrows
    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get the top media files by name")
    @ApiResponse(responseCode = "200", description = "All media names retrieved")
    @Cacheable(value = "topMedias", key = "#numberOfMedias", cacheManager = "cacheManager")
    public List<String> getTopMedia(@RequestParam @NotNull @Min(value = 1) int numberOfMedias)
    {
        return mediaRecommendationService.getTopMedia(numberOfMedias);
    }
}
