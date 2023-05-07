package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.services.AudioService;
import com.emanuel.starterlibrary.dtos.AudioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/audio")
public class AudioController {

    private final AudioService audioService;

    @SneakyThrows
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a audio file")
    @ApiResponse(responseCode = "201", description = "Audio uploaded")
    public AudioDto uploadAudio(
            @NotNull
            @RequestParam("file") MultipartFile file,
            @Parameter(
                    description = "title",
                    schema = @Schema(defaultValue = "Title test")
            )
            @NotEmpty @NotBlank
            String title,
            @Parameter(
                    description = "description",
                    schema = @Schema(defaultValue = "Description test")
            )
            @NotEmpty @NotBlank
            String description
    )
    {
        return audioService.uploadAudio(file, title, description);
    }

    @SneakyThrows
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all audio files")
    @ApiResponse(responseCode = "200", description = "All audio audio retrieved")
    public List<AudioDto> getAllAudios()
    {
        return audioService.getAllAudios();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific audio file based on a giving audio id")
    @ApiResponse(responseCode = "200", description = "Specific audio file retrieved based on a giving audio id")
    public AudioDto getAudioById(@PathVariable @NotNull @Min(value = 0) Long id)
    {
        return audioService.getAudioById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a specific audio file based on a giving audio id")
    @ApiResponse(responseCode = "200", description = "Specific audio file was deleted based on a giving audio id")
    public AudioDto deleteAudio(@PathVariable @NotNull @Min(value = 0) Long id)
    {
        return audioService.deleteAudio(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a specific audio file based on a giving audio id")
    @ApiResponse(responseCode = "200", description = "Specific audio file was updated based on a giving audio id")
    public AudioDto updateAudio(@PathVariable @NotNull @Min(value = 0) Long id, @Valid @RequestBody() AudioDto audio) {
        return audioService.updateAudio(id, audio);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all audio files")
    @ApiResponse(responseCode = "204", description = "All audio files were deleted")
    public void deleteAllAudios(){
        audioService.deleteAllAudios();
    }
}
