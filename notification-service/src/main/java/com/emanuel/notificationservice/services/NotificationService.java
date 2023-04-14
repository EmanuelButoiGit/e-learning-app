package com.emanuel.notificationservice.services;

import com.emanuel.notificationservice.dtos.MetricDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MetricService metricService;
    @Autowired
    private MetricsEndpoint metricsEndpoint;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;
    private static final String H3STYLE = "<h3 style='font-weight: normal;'>";

    public void sendActuatorMetrics() {
        StringBuilder messageBodyBuilder = new StringBuilder();
        messageBodyBuilder.append("<h1>&#x1F31E; Daily Actuator Metrics</h1> <br>");
        messageBodyBuilder.append("<h3 style='font-weight: normal;'> Dear Administrator, <br><br>");
        messageBodyBuilder.append("We hope this message finds you well.<br>" +
                " As part of our ongoing effort to monitor and improve the performance of our systems, " +
                "we would like to provide you with the latest Daily Actuator Metrics report. </h3> <br>");

        List<MetricDto> metrics = metricService.getMetrics();

        metrics.forEach(metric -> {
            Double metricValue = Optional.ofNullable(metricsEndpoint.metric(metric.getName(), null))
                    .map(metricsSummary -> metricsSummary.getMeasurements().get(0).getValue())
                    .orElse(0.0);
            metricValue = metricService.parseMetric(metricValue, metric);
            messageBodyBuilder.append(H3STYLE).append(metric.getEmoji()).append(" ").append(metric.getDescription()).append(" = ")
                    .append(metricValue).append(" <i>").append("  {").append(metric.getName()).append("}").append("</i></h3> <br><br>");
        });

        messageBodyBuilder.append(H3STYLE +
                "Thank you for your continued support in ensuring the reliability and efficiency " +
                "of our systems. <br><br>" +
                "Best regards, <br>" +
                "<b>The Notification Service</b> </h3> <br>");
        sendEmail(messageBodyBuilder, "Daily Actuator Metrics");
    }

    @SneakyThrows
    private void sendEmail(StringBuilder messageBodyBuilder, String subject) {
        String messageBody = messageBodyBuilder.toString();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(username);
        helper.setSubject(subject);
        helper.setText(messageBody, true);
        javaMailSender.send(message);
    }

    public void sendAlert(String alertMessage) {
        StringBuilder messageBodyBuilder = new StringBuilder();
        messageBodyBuilder.append("<h1>&#9888; IMPORTANT! System Alert!</h1> <br>");
        messageBodyBuilder.append("<h3 style='font-weight: normal;'> Dear Administrator, <br><br>");
        messageBodyBuilder.append("We hope this message finds you well.<br>" +
                "As part of our ongoing effort to maintain the reliability and stability of our systems, " +
                "we would like to inform you of a <b>critical issue</b> that requires your attention. " +
                "Our monitoring system has detected a potential problem with one or more components of our system.<br> " +
                "This issue could potentially impact the availability or performance " +
                "of our services and requires immediate attention.</h3> <br><br>");
        messageBodyBuilder.append("<h3>").append(alertMessage).append("</h3><br><br>");
        messageBodyBuilder.append(H3STYLE +
                "We understand the importance of our systems to your operations and apologize for any inconvenience " +
                "or disruption this may cause.<br> " +
                "If you have any questions or concerns, please do not hesitate to contact us." +
                "<br><br>" +
                "Best regards, <br>" +
                "<b>The Notification Service</b> </h3> <br>");
        sendEmail(messageBodyBuilder, "IMPORTANT! System Alert! " + alertMessage);
    }

    public void sendTopMedias() {
        throw new NotImplementedException();
    }

    public void newMedia(String mediaName){
        // NEW Media created
        // A user create a new media at local date with title
        throw new NotImplementedException();
    }
}
