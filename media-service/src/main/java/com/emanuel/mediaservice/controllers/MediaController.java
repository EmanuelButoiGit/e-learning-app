package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.constants.SwaggerConstants;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.services.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public MediaDto uploadMedia(
            @RequestParam("file") MultipartFile file,
            @Parameter(
                    description = "title",
                    schema = @Schema(defaultValue = "Title test")
            )
            String title,
            @Parameter(
                    description = "description",
                    schema = @Schema(defaultValue = "Description test")
            )
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
    public List<MediaDto> getAllMedias()
    {
        return mediaService.getAllMedias();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific media file based on a giving media id")
    @ApiResponse(responseCode = "200", description = "Specific media file retrieved based on a giving media id")
    public MediaDto getMediaById(@PathVariable Long id)
    {
        return mediaService.getMediaById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a specific media file based on a giving media id")
    @ApiResponse(responseCode = "200", description = "Specific media file was deleted based on a giving media id")
    public MediaDto deleteMedia(@PathVariable Long id)
    {
        return mediaService.deleteMedia(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Specific media file was updated based on a giving media id")
    @Operation(
            summary = "Update a specific media file based on a giving media id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MediaDto.class, example = SwaggerConstants.MEDIA_DEFAULT_VALUES)
            )
    ))
    public MediaDto updateToDo(
            @PathVariable("id") Long id,
            @RequestBody() MediaDto media) {

        return mediaService.updateMedia(id, media);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all media files")
    @ApiResponse(responseCode = "204", description = "All media files were deleted")
    public void deleteAllMedias(){
        mediaService.deleteAllMedias();
    }
}
