package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.constants.SwaggerConstants;
import com.emanuel.mediaservice.dtos.ImageDto;
import com.emanuel.mediaservice.dtos.MediaDto;
import com.emanuel.mediaservice.services.ImageService;
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
@RequestMapping("api/image")
public class ImageController {

    private final ImageService imageService;

    @SneakyThrows
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a image file")
    @ApiResponse(responseCode = "201", description = "Image uploaded")
    public ImageDto uploadImage(
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
        return imageService.uploadImage(file, title, description);
    }
}
