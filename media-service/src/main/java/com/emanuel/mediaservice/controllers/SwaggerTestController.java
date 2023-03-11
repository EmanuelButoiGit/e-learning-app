package com.emanuel.mediaservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/")
public class SwaggerTestController {
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Test Swagger V3 with a simple 'Hello World'")
    @ApiResponse(responseCode = "200", description = "Message sent")
    public String getHello(
            @Parameter(
                    description = "name",
                    schema = @Schema(defaultValue = "World")
            )
            String name)
    {
        return "Hello " + name + ", welcome to Swagger V3";
    }
}
