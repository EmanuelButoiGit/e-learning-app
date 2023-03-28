package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.dtos.ImageDto;
import com.emanuel.recommendationservice.dtos.VideoDto;
import com.emanuel.recommendationservice.services.ImageRecommendationService;
import com.emanuel.recommendationservice.services.VideoRecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/recommendation/image")
public class VideoRecommendationController {
    private final VideoRecommendationService videoRecommendationService;

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all recommended video files")
    @ApiResponse(responseCode = "200", description = "All recommended video retrieved")
    public List<VideoDto> getRecommendedVideo(@RequestParam int numberOfVideos)
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
