package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.services.MediaRecommendationService;
import com.emanuel.starterlibrary.dtos.MediaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<MediaDto> getRecommendedMedia(@RequestParam int numberOfMedias)
    {
        return mediaRecommendationService.getRecommendedMedia(numberOfMedias);
    }

    @SneakyThrows
    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get random recommended media file")
    @ApiResponse(responseCode = "200", description = "Random media retrieved")
    public MediaDto getRandomRecommendedMedia()
    {
        return mediaRecommendationService.getRandomRecommendedMedia();
    }

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get the top media files by name")
    @ApiResponse(responseCode = "200", description = "All media names retrieved")
    public List<String> getTopMedia(@RequestParam int numberOfMedias)
    {
        return mediaRecommendationService.getTopMedia(numberOfMedias);
    }
}
