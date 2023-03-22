package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.dtos.AudioDto;
import com.emanuel.recommendationservice.services.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @SneakyThrows
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all recommended audio files")
    @ApiResponse(responseCode = "200", description = "All recommended audio retrieved")
    public List<AudioDto> getRecommendedAudio()
    {
        return recommendationService.getRecommendedAudio();
    }
}
