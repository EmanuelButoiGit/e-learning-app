package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.dtos.ImageDto;
import com.emanuel.recommendationservice.services.ImageRecommendationService;
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
public class ImageRecommendationController {
    private final ImageRecommendationService imageRecommendationService;

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all recommended image files")
    @ApiResponse(responseCode = "200", description = "All recommended image retrieved")
    public List<ImageDto> getRecommendedImage(@RequestParam int numberOfImages)
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
