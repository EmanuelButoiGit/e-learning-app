package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.services.DocumentService;
import com.emanuel.starterlibrary.dtos.DocumentDto;
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
@RequestMapping("api/document")
public class DocumentController {

    private final DocumentService documentService;

    @SneakyThrows
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a document file")
    @ApiResponse(responseCode = "201", description = "Document uploaded")
    public DocumentDto uploadDocument(
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
        return documentService.uploadDocument(file, title, description);
    }

    @SneakyThrows
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all document files")
    @ApiResponse(responseCode = "200", description = "All document files retrieved")
    public List<DocumentDto> getAllDocuments()
    {
        return documentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific document file based on a giving document id")
    @ApiResponse(responseCode = "200", description = "Specific document file retrieved based on a giving document id")
    public DocumentDto getDocumentById(@PathVariable @NotNull @Min(value = 0) Long id)
    {
        return documentService.getDocumentById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a specific document file based on a giving document id")
    @ApiResponse(responseCode = "200", description = "Specific document file was deleted based on a giving document id")
    public DocumentDto deleteDocument(@PathVariable @NotNull @Min(value = 0) Long id)
    {
        return documentService.deleteDocument(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a specific document file based on a giving document id")
    @ApiResponse(responseCode = "200", description = "Specific document file was updated based on a giving document id")
    public DocumentDto updateDocument(@PathVariable @NotNull @Min(value = 0) Long id, @Valid @RequestBody() DocumentDto document) {
        return documentService.updateDocument(id, document);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all document files")
    @ApiResponse(responseCode = "204", description = "All document files were deleted")
    public void deleteAllDocuments(){
        documentService.deleteAllDocuments();
    }
}
