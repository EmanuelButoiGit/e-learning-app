package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.services.ImageRecommendationService;
import com.emanuel.starterlibrary.annotations.Resilient;
import com.emanuel.starterlibrary.dtos.ImageDto;
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
@RequestMapping("api/recommendation/image")
public class ImageRecommendationController {
    private final ImageRecommendationService imageRecommendationService;

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all recommended image files")
    @ApiResponse(responseCode = "200", description = "All recommended image retrieved")
    @Cacheable(value = "recommendedImages", key = "#numberOfImages", cacheManager = "cacheManager")
    public List<ImageDto> getRecommendedImage(@RequestParam @NotNull @Min(value = 1) int numberOfImages)
    {
        return imageRecommendationService.getRecommendedImage(numberOfImages);
    }

    @SneakyThrows
    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get random recommended image file")
    @ApiResponse(responseCode = "200", description = "Random image retrieved")
    public ImageDto getRandomRecommendedImage()
    {
        return imageRecommendationService.getRandomRecommendedImage();
    }
}
