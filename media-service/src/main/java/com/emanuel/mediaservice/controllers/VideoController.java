package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.services.VideoService;
import com.emanuel.starterlibrary.annotations.Resilient;
import com.emanuel.starterlibrary.dtos.VideoDto;
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
@RequestMapping("api/video")
public class VideoController {

    private final VideoService videoService;

    @SneakyThrows
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a video file")
    @ApiResponse(responseCode = "201", description = "Video uploaded")
    @CacheEvict(value = {"videoById", "allVideos"}, allEntries = true)
    public VideoDto uploadVideo(
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
        return videoService.uploadVideo(file, title, description);
    }

    @SneakyThrows
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all video files")
    @ApiResponse(responseCode = "200", description = "All video files retrieved")
    @Cacheable(value = "allVideos", cacheManager = "cacheManager")
    public List<VideoDto> getAllVideos()
    {
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific video file based on a giving video id")
    @ApiResponse(responseCode = "200", description = "Specific video file retrieved based on a giving video id")
    @Cacheable(value = "videoById", key = "#id", cacheManager = "cacheManager")
    public VideoDto getVideoById(@PathVariable @NotNull @Min(value = 0) Long id)
    {
        return videoService.getVideoById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a specific video file based on a giving video id")
    @ApiResponse(responseCode = "200", description = "Specific video file was deleted based on a giving video id")
    @CacheEvict(value = {"videoById", "allVideos"}, allEntries = true)
    public VideoDto deleteVideo(@PathVariable @NotNull @Min(value = 0) Long id)
    {
        return videoService.deleteVideo(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a specific video file based on a giving video id")
    @ApiResponse(responseCode = "200", description = "Specific video file was updated based on a giving video id")
    @CacheEvict(value = {"videoById", "allVideos"}, allEntries = true)
    public VideoDto updateVideo(@PathVariable @NotNull @Min(value = 0) Long id, @Valid @RequestBody() VideoDto video) {
        return videoService.updateVideo(id, video);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all video files")
    @ApiResponse(responseCode = "204", description = "All video files were deleted")
    @CacheEvict(value = {"videoById", "allVideos"}, allEntries = true)
    public void deleteAllVideos(){
        videoService.deleteAllVideos();
    }
}
