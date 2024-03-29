package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.services.ImageService;
import com.emanuel.starterlibrary.annotations.Resilient;
import com.emanuel.starterlibrary.dtos.ImageDto;
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
@RequestMapping("api/image")
public class ImageController {

    private final ImageService imageService;

    @SneakyThrows
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a image file")
    @ApiResponse(responseCode = "201", description = "Image uploaded")
    @CacheEvict(value = {"imageById", "allImages"}, allEntries = true)
    public ImageDto uploadImage(
            @NotNull
            @RequestParam("file") MultipartFile file,
            @Parameter(
                    description = "title",
                    schema = @Schema(defaultValue = "Title test")
            )
            @NotEmpty
            @NotBlank
            String title,
            @Parameter(
                    description = "description",
                    schema = @Schema(defaultValue = "Description test")
            )
            @NotEmpty @NotBlank
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
    @Cacheable(value = "allImages", cacheManager = "cacheManager")
    public List<ImageDto> getAllImages()
    {
        return imageService.getAllImages();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific image file based on a giving image id")
    @ApiResponse(responseCode = "200", description = "Specific image file retrieved based on a giving image id")
    @Cacheable(value = "imageById", key = "#id", cacheManager = "cacheManager")
    public ImageDto getImageById(@PathVariable @NotNull @Min(value = 0) Long id)
    {
        return imageService.getImageById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a specific image file based on a giving image id")
    @ApiResponse(responseCode = "200", description = "Specific image file was deleted based on a giving image id")
    @CacheEvict(value = {"imageById", "allImages"}, allEntries = true)
    public ImageDto deleteImage(@PathVariable @NotNull @Min(value = 0) Long id)
    {
        return imageService.deleteImage(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a specific image file based on a giving image id")
    @ApiResponse(responseCode = "200", description = "Specific image file was updated based on a giving image id")
    @CacheEvict(value = {"imageById", "allImages"}, allEntries = true)
    public ImageDto updateImage(@PathVariable @NotNull @Min(value = 0) Long id, @Valid @RequestBody() ImageDto image) {
        return imageService.updateImage(id, image);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all image files")
    @ApiResponse(responseCode = "204", description = "All image files were deleted")
    @CacheEvict(value = {"imageById", "allImages"}, allEntries = true)
    public void deleteAllImages(){
        imageService.deleteAllImages();
    }
}
