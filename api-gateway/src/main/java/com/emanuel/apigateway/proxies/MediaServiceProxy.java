package com.emanuel.apigateway.proxies;

import com.emanuel.starterlibrary.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient("media-service")
public interface MediaServiceProxy {
    @GetMapping("api/media")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all media files")
    @ApiResponse(responseCode = "200", description = "All media files retrieved")
    List<MediaDto> getAllMedias();
}
