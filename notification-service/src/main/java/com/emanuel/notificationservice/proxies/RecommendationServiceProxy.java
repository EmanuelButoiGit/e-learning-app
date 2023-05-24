package com.emanuel.notificationservice.proxies;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@FeignClient(name = "recommendation-service")
public interface RecommendationServiceProxy {
    @GetMapping("api/recommendation/media/top")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get the top media files by name")
    @ApiResponse(responseCode = "200", description = "All media names retrieved")
    List<String> getTopMedia(@RequestParam @NotNull @Min(value = 1) int numberOfMedias);
}
