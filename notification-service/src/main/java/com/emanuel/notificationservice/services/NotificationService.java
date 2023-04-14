package com.emanuel.notificationservice.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class NotificationService {
    @Autowired
    private MetricsEndpoint metricsEndpoint;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendActuatorMetrics() {
        StringBuilder messageBodyBuilder = new StringBuilder();
        messageBodyBuilder.append("*Actuator Metrics:*\n\n");

        Map<String, String> metrics = new HashMap<>();
        metrics.put("http.server.requests", "Number of HTTP requests received by the server");
        metrics.put("system.cpu.usage", "CPU usage of the entire system");
        metrics.put("tomcat.sessions.rejected", "Number of sessions rejected in Tomcat");
        metrics.put("tomcat.sessions.alive.max", "Maximum time that a session can remain alive in Tomcat");
        metrics.put("tomcat.sessions.created", "Number of sessions created in Tomcat");
        metrics.put("process.cpu.usage", "CPU usage of the current process");
        metrics.put("jvm.threads.live", "Number of live threads in the JVM");
        metrics.put("disk.total", "Total amount of disk space on the system");
        metrics.put("disk.free", "Amount of free disk space on the system");
        metrics.put("tomcat.sessions.expired", "Number of sessions expired in Tomcat");
        metrics.put("jvm.memory.max", "Maximum memory that the JVM can use");
        metrics.put("jvm.gc.pause", "Time spent in GC pauses");
        metrics.put("system.cpu.count", "Number of CPU cores available on the system");
        metrics.put("jvm.memory.used", "Memory used by the JVM");
        metrics.put("application.started.time", "Timestamp indicating when the application was started");
        metrics.put("process.uptime", "Amount of time that the current process has been running");
        metrics.put("tomcat.sessions.active.current", "Current number of active sessions in Tomcat");
        metrics.put("tomcat.sessions.active.max", "Maximum number of active sessions in Tomcat");

        metrics.keySet().forEach(metricName -> {
            Double metricValue = Optional.ofNullable(metricsEndpoint.metric(metricName, null))
                    .map(metricsSummary -> metricsSummary.getMeasurements().get(0).getValue())
                    .orElse(0.0);
            String explanation = metrics.get(metricName);
            messageBodyBuilder.append(explanation).append(" = ").append(metricValue)
                    .append(" <italic>").append(metricName).append("\n\n");
        });

        String messageBody = messageBodyBuilder.toString();

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("anotification99@gmail.com"); // replace with user's email address
            helper.setSubject("Daily Actuator Metrics 🌞");
            helper.setText(messageBody);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            // handle exception
        }
    }
}
