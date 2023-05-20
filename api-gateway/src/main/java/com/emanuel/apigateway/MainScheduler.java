package com.emanuel.apigateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainScheduler {
    private static final String NOTIFICATION_API = "http://notification-service/api/notification";
    private final WebClient.Builder webClientBuilder;

    @Scheduled(cron = "0 0 12 * * ?") // every day at noon
    public void sendActuatorMetrics() {
        webClientBuilder.build()
                .post()
                .uri(NOTIFICATION_API + "/actuator")
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
        log.info("Scheduled job \"sendActuatorMetrics\" was executed");
    }

    @Scheduled(cron = "0 0 9 ? * MON") // every monday at 9 AM
    public void sendTopMedias() {
        webClientBuilder.build()
                .post()
                .uri( NOTIFICATION_API + "/top/medias")
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
        log.info("Scheduled job \"sendTopMedias\" was executed");
    }

    @Scheduled(initialDelay = 3600000, fixedRate = 60000) // run every 1 minute with an initial delay of 1 hour
    public void checkMetrics() {
        webClientBuilder.build()
                .post()
                .uri( NOTIFICATION_API + "/monitor")
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
        log.info("Scheduled job \"checkMetrics\" was executed");
    }
}
