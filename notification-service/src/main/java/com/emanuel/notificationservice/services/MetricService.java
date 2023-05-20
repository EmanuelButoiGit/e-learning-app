package com.emanuel.notificationservice.services;

import com.emanuel.starterlibrary.dtos.MetricDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Service
public class MetricService {
    private List<MetricDto> metrics;
    private NotificationService notificationService;
    @Autowired
    private MetricsEndpoint metricsEndpoint;
    private Double jvmMemoryMax;
    private Double jvmMemoryUsed;

    private static final String CPU_USAGE = "process.cpu.usage";
    private static final String DISK_FREE = "disk.free";
    private static final String JVM_MEM_MAX = "jvm.memory.max";
    private static final String JVM_MEM_USED = "jvm.memory.used";
    @Autowired
    @Lazy
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostConstruct
    public void init() {
        metrics = new ArrayList<>();
        metrics.add(new MetricDto("http.server.requests", "Number of HTTP requests received by the server","&#128214;"));
        metrics.add(new MetricDto("system.cpu.usage", "CPU usage of the entire system","&#128187;"));
        metrics.add(new MetricDto("tomcat.sessions.rejected", "Number of sessions rejected in Tomcat","&#128683;"));
        metrics.add(new MetricDto("tomcat.sessions.alive.max", "Maximum time that a session can remain alive in Tomcat","&#9200;"));
        metrics.add(new MetricDto("tomcat.sessions.created", "Number of sessions created in Tomcat","&#127381;"));
        metrics.add(new MetricDto(CPU_USAGE, "CPU usage of the current process (%)","&#9881;"));
        metrics.add(new MetricDto("jvm.threads.live", "Number of live threads in the JVM","&#128161;"));
        metrics.add(new MetricDto("disk.total", "Total amount of disk space on the system (GB)","&#128190;"));
        metrics.add(new MetricDto(DISK_FREE, "Amount of free disk space on the system (GB)","&#128191;"));
        metrics.add(new MetricDto("tomcat.sessions.expired", "Number of sessions expired in Tomcat","&#9203;"));
        metrics.add(new MetricDto(JVM_MEM_MAX, "Maximum memory that the JVM can use (GB)","&#128285;"));
        metrics.add(new MetricDto("jvm.gc.pause", "Time spent in GC pauses","&#128687;"));
        metrics.add(new MetricDto("system.cpu.count", "Number of CPU cores available on the system","&#128187;"));
        metrics.add(new MetricDto(JVM_MEM_USED, "Memory used by the JVM (GB)","&#128202;"));
        metrics.add(new MetricDto("application.started.time", "Unix Timestamp indicating when the application was started","&#128336;"));
        metrics.add(new MetricDto("process.uptime", "Amount of time that the current process has been running","&#9201;"));
        metrics.add(new MetricDto("tomcat.sessions.active.current", "Current number of active sessions in Tomcat","&#128101;"));
        metrics.add(new MetricDto("tomcat.sessions.active.max", "Maximum number of active sessions in Tomcat","&#128285;"));
    }

    public double parseMetric(Double metricValue, MetricDto metric){
        if (CPU_USAGE.equals(metric.getName())) {
            return metricValue * 100;
        } else if ("disk.total".equals(metric.getName()) || DISK_FREE.equals(metric.getName())
                || JVM_MEM_MAX.equals(metric.getName()) || JVM_MEM_USED.equals(metric.getName())) {
            return metricValue / Math.pow(1024, 3);
        } else {
            return metricValue;
        }
    }

    public void checkMetrics(){
        metrics.forEach(metric -> {
            double metricValue = Optional.ofNullable(metricsEndpoint.metric(metric.getName(), null))
                    .map(metricsSummary -> metricsSummary.getMeasurements().get(0).getValue())
                    .orElse(0.0);
            metricValue = parseMetric(metricValue, metric);
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
        } else if (metric.getName().equals(CPU_USAGE) && metricValue >= 75) {
            notificationService.sendAlert("Process CPU usage is high, " + metricValue + "%");
        } else if (metric.getName().equals("jvm.threads.live") && metricValue >= 100) {
            notificationService.sendAlert("Number of live threads in JVM is high, it is equal to " + metricValue);
        } else if (metric.getName().equals(DISK_FREE) && metricValue <= 50) {
            notificationService.sendAlert("Free disk space is low. The system has " + metricValue + " GB left.");
        } else if (metric.getName().equals(JVM_MEM_MAX)) {
            this.jvmMemoryMax = metricValue;
        } else if (metric.getName().equals("jvm.gc.pause") && metricValue >= 500) {
            notificationService.sendAlert("GC pause time is high, it is equal to " + metricValue + "ms");
        } else if (metric.getName().equals(JVM_MEM_USED)) {
            this.jvmMemoryUsed = metricValue;
        } else if (metric.getName().equals("tomcat.sessions.active.max") && metricValue >= 100) {
            notificationService.sendAlert("Max active sessions in Tomcat is high, it is equal to " + metricValue);
        }
    }
}
