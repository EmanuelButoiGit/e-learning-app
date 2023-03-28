package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.services.ImageService;
import com.emanuel.starterlibrary.dtos.ImageDto;
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

    @SneakyThrows
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all image files")
    @ApiResponse(responseCode = "200", description = "All image files retrieved")
    public List<ImageDto> getAllImages()
    {
        return imageService.getAllImages();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific image file based on a giving image id")
    @ApiResponse(responseCode = "200", description = "Specific image file retrieved based on a giving image id")
    public ImageDto getImageById(@PathVariable Long id)
    {
        return imageService.getImageById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a specific image file based on a giving image id")
    @ApiResponse(responseCode = "200", description = "Specific image file was deleted based on a giving image id")
    public ImageDto deleteImage(@PathVariable Long id)
    {
        return imageService.deleteImage(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a specific image file based on a giving image id")
    @ApiResponse(responseCode = "200", description = "Specific image file was updated based on a giving image id")
    public ImageDto updateImage(@PathVariable("id") Long id, @RequestBody() ImageDto image) {
        return imageService.updateImage(id, image);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all image files")
    @ApiResponse(responseCode = "204", description = "All image files were deleted")
    public void deleteAllImages(){
        imageService.deleteAllImages();
    }
}
