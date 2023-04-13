package com.emanuel.notificationservice.controllers;

import com.emanuel.notificationservice.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Send actuator notification")
    @ApiResponse(responseCode = "200", description = "Notification sent")
    public void sendActuatorMetrics()
    {
        notificationService.sendActuatorMetrics();
    }
}
