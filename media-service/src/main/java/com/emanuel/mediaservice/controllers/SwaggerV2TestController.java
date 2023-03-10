package com.emanuel.mediaservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
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
public class SwaggerV2TestController {
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "test swagger")
    @ApiResponse(responseCode = "200", description = "get greetings")
    public String getHello(String name){
        return "Hello " + name;
    }
}
