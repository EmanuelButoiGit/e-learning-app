package com.emanuel.ratingservice.proxies;

import com.emanuel.starterlibrary.dtos.MediaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@FeignClient(name = "media-service")
public interface MediaServiceProxy {
    @SuppressWarnings("UnusedReturnValue")
    @GetMapping("api/media/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a specific media file based on a giving media id")
    @ApiResponse(responseCode = "200", description = "Specific media file retrieved based on a giving media id")
    MediaDto getMediaById(@PathVariable @NotNull @Min(value = 0) Long id);
}
