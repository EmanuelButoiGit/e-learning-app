package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.services.DocumentRecommendationService;
import com.emanuel.starterlibrary.annotations.Resilient;
import com.emanuel.starterlibrary.dtos.DocumentDto;
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
@RequestMapping("api/recommendation/document")
public class DocumentRecommendationController {
    private final DocumentRecommendationService documentRecommendationService;

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all recommended document files")
    @ApiResponse(responseCode = "200", description = "All recommended document retrieved")
    @Cacheable(value = "recommendedDocuments", key = "#numberOfDocuments", cacheManager = "cacheManager")
    public List<DocumentDto> getRecommendedDocument(@RequestParam @NotNull @Min(value = 1) int numberOfDocuments)
    {
        return documentRecommendationService.getRecommendedDocument(numberOfDocuments);
    }

    @SneakyThrows
    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get random recommended document file")
    @ApiResponse(responseCode = "200", description = "Random document retrieved")
    public DocumentDto getRandomRecommendedAudio()
    {
        return documentRecommendationService.getRandomRecommendedDocument();
    }
}
