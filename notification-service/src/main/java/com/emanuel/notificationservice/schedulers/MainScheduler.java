package com.emanuel.notificationservice.schedulers;

import com.emanuel.notificationservice.dtos.MetricDto;
import com.emanuel.notificationservice.services.MetricService;
import com.emanuel.notificationservice.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MainScheduler {
    private final NotificationService notificationService;
    private final MetricService metricService;
    @Autowired
    private MetricsEndpoint metricsEndpoint;
    private Double jvmMemoryMax;
    private Double jvmMemoryUsed;

    @Scheduled(cron = "0 0 7 * * ?")
    public void sendActuatorMetrics() {
        notificationService.sendActuatorMetrics();
    }

    @Scheduled(cron = "0 0 12 * * ?") // every day at noon
    public void sendTopMedias() {
        notificationService.sendTopMedias();
    }

    @Scheduled(fixedRate = 60000 * 60) // run every 1 minute (60) // TO DO: put back to 1 minute and add initial delay of 1 hour
    public void checkMetrics() {
        List<MetricDto> metrics = metricService.getMetrics();
        metrics.forEach(metric -> {
            double metricValue = Optional.ofNullable(metricsEndpoint.metric(metric.getName(), null))
                    .map(metricsSummary -> metricsSummary.getMeasurements().get(0).getValue())
                    .orElse(0.0);
            metricValue = metricService.parseMetric(metricValue, metric);
            checkMetricValue(metric, metricValue);
        });
        double jvmMemoryUsage = this.jvmMemoryUsed / this.jvmMemoryMax;
        if (jvmMemoryUsage >= 80) {
            notificationService.sendAlert("JVM memory usage is high, it is " + jvmMemoryUsage + "%");
        }
    }

    @SuppressWarnings("squid:S3776")
    private void checkMetricValue(MetricDto metric, double metricValue) {
        if (metric.getName().equals("http.server.requests") && metricValue == 0) {
            notificationService.sendAlert("HTTP requests count is 0");
        } else if (metric.getName().equals("system.cpu.usage") && metricValue == 0) {
            notificationService.sendAlert("CPU usage is 0");
        } else if (metric.getName().equals("tomcat.sessions.rejected") && metricValue == 0){
            notificationService.sendAlert("Tomcat sessions are being rejected");
        } else if (metric.getName().equals("tomcat.sessions.alive.max") && metricValue == 0) {
            notificationService.sendAlert("Maximum session alive time in Tomcat is 0");
        } else if (metric.getName().equals("process.cpu.usage") && metricValue >= 75) {
            notificationService.sendAlert("Process CPU usage is high, " + metricValue + "%");
        } else if (metric.getName().equals("jvm.threads.live") && metricValue >= 100) {
            notificationService.sendAlert("Number of live threads in JVM is high, it is equal to " + metricValue);
        } else if (metric.getName().equals("disk.free") && metricValue <= 50) {
            notificationService.sendAlert("Free disk space is low. The system has " + metricValue + " GB left.");
        } else if (metric.getName().equals("jvm.memory.max")) {
            this.jvmMemoryMax = metricValue;
        } else if (metric.getName().equals("jvm.gc.pause") && metricValue >= 500) {
            notificationService.sendAlert("GC pause time is high, it is equal to " + metricValue + "ms");
        } else if (metric.getName().equals("jvm.memory.used")) {
            this.jvmMemoryUsed = metricValue;
        } else if (metric.getName().equals("tomcat.sessions.active.max") && metricValue >= 100) {
            notificationService.sendAlert("Max active sessions in Tomcat is high, it is equal to " + metricValue);
        }
    }
}
