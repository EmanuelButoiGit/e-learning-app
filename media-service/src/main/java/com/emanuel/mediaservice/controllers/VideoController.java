package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.dtos.VideoDto;
import com.emanuel.mediaservice.services.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("api/video")
public class VideoController {

    private final VideoService videoService;

    @SneakyThrows
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a video file")
    @ApiResponse(responseCode = "201", description = "Video uploaded")
    public VideoDto uploadVideo(
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
        return videoService.uploadVideo(file, title, description);
    }

    @SneakyThrows
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all video files")
    @ApiResponse(responseCode = "200", description = "All video files retrieved")
    public List<VideoDto> getAllVideos()
    {
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific video file based on a giving video id")
    @ApiResponse(responseCode = "200", description = "Specific video file retrieved based on a giving video id")
    public VideoDto getVideoById(@PathVariable Long id)
    {
        return videoService.getVideoById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a specific video file based on a giving video id")
    @ApiResponse(responseCode = "200", description = "Specific video file was deleted based on a giving video id")
    public VideoDto deleteVideo(@PathVariable Long id)
    {
        return videoService.deleteVideo(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a specific video file based on a giving video id")
    @ApiResponse(responseCode = "200", description = "Specific video file was updated based on a giving video id")
    public VideoDto updateVideo(@PathVariable("id") Long id, @RequestBody() VideoDto video) {
        return videoService.updateVideo(id, video);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all video files")
    @ApiResponse(responseCode = "204", description = "All video files were deleted")
    public void deleteAllVideos(){
        videoService.deleteAllVideos();
    }
}
