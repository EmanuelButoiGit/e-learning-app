package com.emanuel.recommendationservice.proxies;

import com.emanuel.starterlibrary.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.SneakyThrows;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient("media-service")
public interface MediaServiceProxy {
    @SneakyThrows
    @GetMapping("api/media")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all media files")
    @ApiResponse(responseCode = "200", description = "All media files retrieved")
    List<MediaDto> getAllMedias();

    @SneakyThrows
    @GetMapping("api/audio")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all audio files")
    @ApiResponse(responseCode = "200", description = "All audio audio retrieved")
    List<AudioDto> getAllAudios();

    @SneakyThrows
    @GetMapping("api/document")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all document files")
    @ApiResponse(responseCode = "200", description = "All document files retrieved")
    List<DocumentDto> getAllDocuments();

    @SneakyThrows
    @GetMapping("api/image")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all image files")
    @ApiResponse(responseCode = "200", description = "All image files retrieved")
    List<ImageDto> getAllImages();

    @SneakyThrows
    @GetMapping("api/video")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all video files")
    @ApiResponse(responseCode = "200", description = "All video files retrieved")
    List<VideoDto> getAllVideos();
}
