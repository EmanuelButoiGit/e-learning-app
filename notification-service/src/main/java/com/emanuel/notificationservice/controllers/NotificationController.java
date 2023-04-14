package com.emanuel.notificationservice.controllers;

import com.emanuel.notificationservice.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/actuator")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Send actuator notification")
    @ApiResponse(responseCode = "200", description = "Notification sent")
    public void sendActuatorMetrics()
    {
        notificationService.sendActuatorMetrics();
    }

    @PostMapping("/alert")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Send alert notification")
    @ApiResponse(responseCode = "200", description = "Alert notification sent")
    public void sendAlert(
            @Parameter(
                    description = "alert",
                    schema = @Schema(defaultValue = "Alert test")
            )
            String alert
    )
    {
        notificationService.sendAlert(alert);
    }

    @PostMapping("/top/medias")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Send top medias notification")
    @ApiResponse(responseCode = "200", description = "Top medias notification sent")
    public void sendTopMedias()
    {
        notificationService.sendTopMedias();
    }
}
