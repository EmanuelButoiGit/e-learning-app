package com.emanuel.notificationservice.services;

import com.emanuel.notificationservice.dtos.MetricDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class NotificationService {
    @Autowired
    private MetricsEndpoint metricsEndpoint;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;

    @SneakyThrows
    public void sendActuatorMetrics() {
        StringBuilder messageBodyBuilder = new StringBuilder();
        messageBodyBuilder.append("<h1>&#x1F31E; Daily Actuator Metrics</h1> <br><br>");

        List<MetricDto> metrics = new ArrayList<>();
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

        metrics.forEach(metric -> {
            Double metricValue = Optional.ofNullable(metricsEndpoint.metric(metric.getName(), null))
                    .map(metricsSummary -> metricsSummary.getMeasurements().get(0).getValue())
                    .orElse(0.0);
            if ("process.cpu.usage".equals(metric.getName())) {
                metricValue *= 100;
            } else if ("disk.total".equals(metric.getName()) || "disk.free".equals(metric.getName())
                    || "jvm.memory.max".equals(metric.getName()) || "jvm.memory.used".equals(metric.getName())) {
                metricValue /= Math.pow(1024, 3);
            }
            messageBodyBuilder.append("<h3 style='font-weight: normal;'>").append(metric.getEmoji()).append(" ").append(metric.getDescription()).append(" = ")
                    .append(metricValue).append(" <i>").append("  {").append(metric.getName()).append("}").append("</i></h3> <br><br>");
        });

        String messageBody = messageBodyBuilder.toString();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(username);
        helper.setSubject("Daily Actuator Metrics");
        helper.setText(messageBody, true);
        javaMailSender.send(message);
    }
}
