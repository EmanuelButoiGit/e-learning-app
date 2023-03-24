package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.dtos.AudioDto;
import com.emanuel.recommendationservice.services.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/recommendation/audio")
public class AudioRecommendationController {
    private final RecommendationService recommendationService;

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all recommended audio files")
    @ApiResponse(responseCode = "200", description = "All recommended audio retrieved")
    public List<AudioDto> getRecommendedAudio(@RequestParam int numberOfAudios)
    {
        return recommendationService.getRecommendedAudio(numberOfAudios);
    }

    @SneakyThrows
    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get random recommended audio file")
    @ApiResponse(responseCode = "200", description = "Random audio retrieved")
    public AudioDto getRandomRecommendedAudio()
    {
        return recommendationService.getRandomRecommendedAudio();
    }
}
