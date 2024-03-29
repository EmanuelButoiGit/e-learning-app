package com.emanuel.notificationservice.controllers;

import com.emanuel.notificationservice.services.MetricService;
import com.emanuel.notificationservice.services.NotificationService;
import com.emanuel.starterlibrary.annotations.Resilient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Resilient
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final MetricService metricService;

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
            @NotEmpty @NotBlank
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
            @NotEmpty @NotBlank
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

    @PostMapping("/monitor")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Check if the metrics are ok")
    @ApiResponse(responseCode = "200", description = "The verification was successful")
    public void checkMetrics()
    {
        metricService.checkMetrics();
    }
}
