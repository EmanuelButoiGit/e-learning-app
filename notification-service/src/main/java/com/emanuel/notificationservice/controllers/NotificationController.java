package com.emanuel.notificationservice.controllers;

import com.emanuel.notificationservice.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam String alert
    )
    {
        notificationService.sendAlert(alert);
    }

    @PostMapping("/new/media")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Send new media notification")
    @ApiResponse(responseCode = "200", description = "New media notification sent")
    public void sendNewMediaNotification(
            @Parameter(
                    description = "new media",
                    schema = @Schema(defaultValue = "New media test")
            )
            @RequestParam String newMedia
    )
    {
        notificationService.sendNewMediaNotification(newMedia);
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
