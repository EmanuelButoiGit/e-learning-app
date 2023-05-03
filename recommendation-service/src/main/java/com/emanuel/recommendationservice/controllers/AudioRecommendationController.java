package com.emanuel.recommendationservice.controllers;

import com.emanuel.recommendationservice.services.AudioRecommendationService;
import com.emanuel.starterlibrary.dtos.AudioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/recommendation/audio")
public class AudioRecommendationController {
    private final AudioRecommendationService audioRecommendationService;

    @SneakyThrows
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all recommended audio files")
    @ApiResponse(responseCode = "200", description = "All recommended audio retrieved")
    public List<AudioDto> getRecommendedAudio(@RequestParam @NotNull @Min(value = 1) int numberOfAudios)
    {
        return audioRecommendationService.getRecommendedAudio(numberOfAudios);
    }

    @SneakyThrows
    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get random recommended audio file")
    @ApiResponse(responseCode = "200", description = "Random audio retrieved")
    public AudioDto getRandomRecommendedAudio()
    {
        return audioRecommendationService.getRandomRecommendedAudio();
    }
}
