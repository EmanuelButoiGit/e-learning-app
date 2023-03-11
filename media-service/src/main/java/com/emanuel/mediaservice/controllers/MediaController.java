package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.services.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/media")
public class MediaController {

    private final UploadService uploadService;

    @SneakyThrows
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a media file")
    @ApiResponse(responseCode = "201", description = "Media uploaded")
    public MediaDto uploadMedia(@RequestParam("file") MultipartFile file, String description)
    {
        return uploadService.saveFile(file, description);
    }
}
