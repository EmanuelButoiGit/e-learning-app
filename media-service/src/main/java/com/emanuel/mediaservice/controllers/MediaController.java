package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.services.MediaService;
import com.emanuel.starterlibrary.annotations.Resilient;
import com.emanuel.starterlibrary.dtos.MediaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

@Resilient
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/media")
public class MediaController {

    private final MediaService mediaService;

    @SneakyThrows
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a media file")
    @ApiResponse(responseCode = "201", description = "Media uploaded")
    @CacheEvict(value = {"mediaById", "allMedias"}, allEntries = true)
    public MediaDto uploadMedia(
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
        return mediaService.uploadMedia(file, title, description);
    }

    @SneakyThrows
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all media files")
    @ApiResponse(responseCode = "200", description = "All media files retrieved")
    @Cacheable(value = "allMedias", cacheManager = "cacheManager")
    public List<MediaDto> getAllMedias()
    {
        return mediaService.getAllMedias();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific media file based on a giving media id")
    @ApiResponse(responseCode = "200", description = "Specific media file retrieved based on a giving media id")
    @Cacheable(value = "mediaById", key = "#id", cacheManager = "cacheManager")
    public MediaDto getMediaById(@PathVariable @NotNull @Min(value = 0) Long id)
    {
        return mediaService.getMediaById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a specific media file based on a giving media id")
    @ApiResponse(responseCode = "200", description = "Specific media file was deleted based on a giving media id")
    @CacheEvict(value = {"mediaById", "allMedias"}, allEntries = true)
    public MediaDto deleteMedia(@PathVariable @NotNull @Min(value = 0) Long id)
    {
        return mediaService.deleteMedia(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a specific media file based on a giving media id")
    @ApiResponse(responseCode = "200", description = "Specific media file was updated based on a giving media id")
    @CacheEvict(value = {"mediaById", "allMedias"}, allEntries = true)
    public MediaDto updateMedia(@PathVariable @NotNull @Min(value = 0) Long id, @Valid @RequestBody() MediaDto media) {
        return mediaService.updateMedia(id, media);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all media files")
    @ApiResponse(responseCode = "204", description = "All media files were deleted")
    @CacheEvict(value = {"mediaById", "allMedias"}, allEntries = true)
    public void deleteAllMedias(){
        mediaService.deleteAllMedias();
    }
}
