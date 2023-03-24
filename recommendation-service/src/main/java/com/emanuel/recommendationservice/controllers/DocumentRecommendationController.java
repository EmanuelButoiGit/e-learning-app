package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.dtos.AudioDto;
import com.emanuel.recommendationservice.dtos.DocumentDto;
import com.emanuel.recommendationservice.services.AudioRecommendationService;
import com.emanuel.recommendationservice.services.DocumentRecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<DocumentDto> getRecommendedDocument(@RequestParam int numberOfDocuments)
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
