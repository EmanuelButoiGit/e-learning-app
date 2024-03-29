package com.emanuel.notificationservice.services;

import com.emanuel.notificationservice.proxies.RecommendationServiceProxy;
import com.emanuel.starterlibrary.dtos.MetricDto;
import com.emanuel.starterlibrary.exceptions.DeserializationException;
import com.emanuel.starterlibrary.exceptions.EmailException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.*;

@Slf4j
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
    private final RecommendationServiceProxy recommendationServiceProxy;
    private static final String CLOSE_TITLE_TAGS = "</h1> <br>";
    private static final String H3STYLE = "<h3 style='font-weight: normal;'>";
    private static final String H2STYLE = "<h2 style='font-weight: normal;'>";
    private static final String INTRO = "<h3 style='font-weight: normal;'> Dear Administrator, <br><br>" +
            "We hope this message finds you well.<br>";
    private static final String OUTRO = H3STYLE +
            "As always, we welcome any feedback or suggestions you may have for how we can improve " +
            "our platform and better serve our users.<br>" +
            "We are committed to providing you with the best possible experience, " +
            "and we appreciate your ongoing support.";
    private static final String ENDING = "<br><br>Best regards,<br>" +
                "<b>The Notification Service</b><br><br>" +
                "For any additional information please contact:<br>" +
                "Software Engineer<br>" +
                "Butoi Emanuel-Sebastian<br>" +
                "https://github.com/EmanuelButoiGit<br>" +
                "</h3> <br>";

    public void sendActuatorMetrics() {
        String subject = "Daily Actuator Metrics";
        StringBuilder messageBodyBuilder = new StringBuilder();
        messageBodyBuilder.append("<h1>&#x1F31E; ").append(subject).append(CLOSE_TITLE_TAGS);
        messageBodyBuilder.append(INTRO).append(
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
                "Thank you for your continued trust in us in ensuring reliable and efficient systems for you.<br> " +
                "We are committed to providing you with the best possible experience, " +
                "and we appreciate your ongoing support.")
                .append(ENDING);
        sendEmail(messageBodyBuilder, subject);
    }

    @SneakyThrows
    private void sendEmail(StringBuilder messageBodyBuilder,  @NotEmpty @NotBlank String subject) {
        String messageBody = messageBodyBuilder.toString();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(username);
        helper.setSubject(subject);
        helper.setText(messageBody, true);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new EmailException(e, subject);
        }
        log.info("The mail with the following subject \"{}\" was sent", subject);
    }

    public void sendAlert(@NotEmpty @NotBlank String alertMessage) {
        StringBuilder messageBodyBuilder = new StringBuilder();
        String subject = "IMPORTANT! System Alert!";
        messageBodyBuilder.append("<h1>&#9888; ").append(subject).append(CLOSE_TITLE_TAGS);
        messageBodyBuilder.append(INTRO).append(
                "As part of our ongoing effort to maintain the reliability and stability of our systems, " +
                "we would like to inform you of a <b>critical issue</b> that requires your attention. " +
                "Our monitoring system has detected a potential problem with one or more components of our system.<br> " +
                "This issue could potentially impact the availability or performance " +
                "of our services and <b>requires immediate attention</b>.</h3> <br>");
        messageBodyBuilder.append("<h1>").append(alertMessage).append("</h1><br>");
        messageBodyBuilder.append(H3STYLE +
                        "We understand the importance of our systems to your operations and apologize for any inconvenience " +
                        "or disruption this may cause.<br> " +
                        "If you have any questions or concerns, please do not hesitate to contact us.")
                .append(ENDING);
        sendEmail(messageBodyBuilder, subject);
    }

    @SneakyThrows
    public void sendTopMedias() {
        List<String> medias = recommendationServiceProxy.getTopMedia(10);
        if (medias == null) {
            throw new NullPointerException("The list is empty!");
        }
        StringBuilder messageBodyBuilder = new StringBuilder();
        String subject = "Weekly top medias";
        messageBodyBuilder.append("<h1>&#11088; ").append(subject).append(CLOSE_TITLE_TAGS);
        messageBodyBuilder.append(INTRO).append(
                "I am writing to provide you with a weekly update on the top medias on our platform.<br>" +
                        "Over the past week, we have seen a significant increase in user engagement, " +
                        "and I am pleased to report that the following medias have been particularly successful:" +
                        "</h3> <br>");

        messageBodyBuilder.append("<ol>");
        medias.forEach((String media) -> messageBodyBuilder.append("<b><li>").append(media).append("</li></b>"));
        messageBodyBuilder.append("</ol>");

        messageBodyBuilder.append("<br>");
        messageBodyBuilder.append(OUTRO)
                .append(ENDING);
        sendEmail(messageBodyBuilder, subject);
    }

    public void sendNewMediaNotification(@NotEmpty @NotBlank String mediaName){
        StringBuilder messageBodyBuilder = new StringBuilder();
        messageBodyBuilder.append("<h1>&#127881; New media created!").append(CLOSE_TITLE_TAGS);
        messageBodyBuilder.append(INTRO).append(
                "I am pleased to inform you that a user has successfully created a new media in our system." +
                "</h3><br>");
        messageBodyBuilder.append(H2STYLE).append("Feel free to checkout & experience: <b>\"").append(mediaName)
                .append("\"</b></h2><br>");
        messageBodyBuilder.append(OUTRO)
                .append(ENDING);
        sendEmail(messageBodyBuilder, "New added media: \"" + mediaName + "\"");
    }
}
