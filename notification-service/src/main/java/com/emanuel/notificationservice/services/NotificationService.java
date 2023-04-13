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
        messageBodyBuilder.append("Actuator Metrics:\n\n");

        String[] metricNames = {"jvm.memory.used", "jvm.threads.live", "process.cpu.usage"};
        for (String metricName : metricNames) {
            Object metricValue = metricsEndpoint.metric(metricName, null).getMeasurements().get(0).getValue();
            messageBodyBuilder.append(metricName).append("=").append(metricValue).append("\n");
        }

        String messageBody = messageBodyBuilder.toString();

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("anotification99@gmail.com"); // replace with user's email address
            helper.setSubject("Daily Actuator Metrics");
            helper.setText(messageBody);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            // handle exception
        }
    }
}
