package com.emanuel.notificationservice.services;

import com.emanuel.notificationservice.dtos.MetricDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MetricService {
    private List<MetricDto> metrics;

    @PostConstruct
    public void init() {
        metrics = new ArrayList<>();
        metrics.add(new MetricDto("http.server.requests", "Number of HTTP requests received by the server","&#128214;"));
        metrics.add(new MetricDto("system.cpu.usage", "CPU usage of the entire system","&#128187;"));
        metrics.add(new MetricDto("tomcat.sessions.rejected", "Number of sessions rejected in Tomcat","&#128683;"));
        metrics.add(new MetricDto("tomcat.sessions.alive.max", "Maximum time that a session can remain alive in Tomcat","&#9200;"));
        metrics.add(new MetricDto("tomcat.sessions.created", "Number of sessions created in Tomcat","&#127381;"));
        metrics.add(new MetricDto("process.cpu.usage", "CPU usage of the current process (%)","&#9881;"));
        metrics.add(new MetricDto("jvm.threads.live", "Number of live threads in the JVM","&#128161;"));
        metrics.add(new MetricDto("disk.total", "Total amount of disk space on the system (GB)","&#128190;"));
        metrics.add(new MetricDto("disk.free", "Amount of free disk space on the system (GB)","&#128191;"));
        metrics.add(new MetricDto("tomcat.sessions.expired", "Number of sessions expired in Tomcat","&#9203;"));
        metrics.add(new MetricDto("jvm.memory.max", "Maximum memory that the JVM can use (GB)","&#128285;"));
        metrics.add(new MetricDto("jvm.gc.pause", "Time spent in GC pauses","&#128687;"));
        metrics.add(new MetricDto("system.cpu.count", "Number of CPU cores available on the system","&#128187;"));
        metrics.add(new MetricDto("jvm.memory.used", "Memory used by the JVM (GB)","&#128202;"));
        metrics.add(new MetricDto("application.started.time", "Unix Timestamp indicating when the application was started","&#128336;"));
        metrics.add(new MetricDto("process.uptime", "Amount of time that the current process has been running","&#9201;"));
        metrics.add(new MetricDto("tomcat.sessions.active.current", "Current number of active sessions in Tomcat","&#128101;"));
        metrics.add(new MetricDto("tomcat.sessions.active.max", "Maximum number of active sessions in Tomcat","&#128285;"));
    }

    public double parseMetric(Double metricValue, MetricDto metric){
        if ("process.cpu.usage".equals(metric.getName())) {
            return metricValue * 100;
        } else if ("disk.total".equals(metric.getName()) || "disk.free".equals(metric.getName())
                || "jvm.memory.max".equals(metric.getName()) || "jvm.memory.used".equals(metric.getName())) {
            return metricValue / Math.pow(1024, 3);
        } else {
            return metricValue;
        }
    }
}
