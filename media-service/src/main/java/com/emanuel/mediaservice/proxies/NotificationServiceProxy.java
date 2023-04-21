package com.emanuel.mediaservice.proxies;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "notification-service")
public interface NotificationServiceProxy {
    @PostMapping("api/notification/new/media")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Send new media notification")
    @ApiResponse(responseCode = "200", description = "New media notification sent")
    void sendNewMediaNotification(
            @Parameter(
                    description = "new media",
                    schema = @Schema(defaultValue = "New media test")
            )
            @RequestParam String newMedia
    );
}
