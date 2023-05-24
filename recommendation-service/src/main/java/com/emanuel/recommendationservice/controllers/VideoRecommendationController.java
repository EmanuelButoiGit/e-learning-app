package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.services.VideoRecommendationService;
import com.emanuel.starterlibrary.annotations.Resilient;
import com.emanuel.starterlibrary.dtos.VideoDto;
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
@RequestMapping("api/recommendation/video")
public class VideoRecommendationController {
    private final VideoRecommendationService videoRecommendationService;

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all recommended video files")
    @ApiResponse(responseCode = "200", description = "All recommended video retrieved")
    @Cacheable(value = "recommendedVideos", key = "#numberOfVideos", cacheManager = "cacheManager")
    public List<VideoDto> getRecommendedVideo(@RequestParam @NotNull @Min(value = 1) int numberOfVideos)
    {
        return videoRecommendationService.getRecommendedVideo(numberOfVideos);
    }

    @SneakyThrows
    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get random recommended video file")
    @ApiResponse(responseCode = "200", description = "Random video retrieved")
    public VideoDto getRandomRecommendedVideo()
    {
        return videoRecommendationService.getRandomRecommendedVideo();
    }
}
