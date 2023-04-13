package com.emanuel.notificationservice.schedulers;

import com.emanuel.notificationservice.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ActuatorMetricsScheduler {
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 12 * * ?") // every day at noon
    public void sendActuatorMetrics() {
        notificationService.sendActuatorMetrics();
    }
}
