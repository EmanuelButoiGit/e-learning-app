package com.emanuel.notificationservice.schedulers;

import com.emanuel.notificationservice.services.MetricService;
import com.emanuel.notificationservice.services.NotificationService;
import com.emanuel.starterlibrary.dtos.MetricDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainScheduler {
    private final NotificationService notificationService;
    private final MetricService metricService;

    @Scheduled(cron = "0 0 12 * * ?") // every day at noon
    public void sendActuatorMetrics() {
        notificationService.sendActuatorMetrics();
        log.info("Scheduled job sendActuatorMetrics was executed");
    }

    @Scheduled(cron = "0 0 9 ? * MON") // every monday at 9 AM
    public void sendTopMedias() {
        notificationService.sendTopMedias();
        log.info("Scheduled job sendTopMedias was executed");
    }

    @Scheduled(initialDelay = 3600000, fixedRate = 60000) // run every 1 minute with an initial delay of 1 hour
    public void checkMetrics() {
        metricService.checkMetrics();
        log.info("Scheduled job checkMetrics was executed");
    }
}
