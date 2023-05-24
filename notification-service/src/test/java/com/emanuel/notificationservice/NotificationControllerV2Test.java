package com.emanuel.notificationservice;

import com.emanuel.notificationservice.services.MetricService;
import com.emanuel.notificationservice.services.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class NotificationControllerV2Test {
    @Autowired
    private TestRestTemplate rest;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private MetricService metricService;

    @Test
    void sendActuatorMetricsTest() {
        // assume
        Mockito.doNothing().when(notificationService).sendActuatorMetrics();

        // act
        ResponseEntity<Void> result = rest.postForEntity("/api/notification/actuator", null, Void.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(notificationService, Mockito.times(1)).sendActuatorMetrics();
    }

    @Test
    void sendAlertTest() {
        // assume
        final String alert = "Alert Test";
        Mockito.doNothing().when(notificationService).sendAlert(alert);

        // act
        ResponseEntity<Void> result = rest.postForEntity("/api/notification/alert?alert=" + alert, null, Void.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(notificationService, Mockito.times(1)).sendAlert(alert);
    }

    @Test
    void sendNewMediaNotificationTest() {
        // assume
        final String media = "New Media Test";
        Mockito.doNothing().when(notificationService).sendNewMediaNotification(media);

        // act
        ResponseEntity<Void> result = rest.postForEntity("/api/notification/new/media?newMedia=" + media, null, Void.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(notificationService, Mockito.times(1)).sendNewMediaNotification(media);
    }

    @Test
    void sendTopMediasTest() {
        // assume
        Mockito.doNothing().when(notificationService).sendTopMedias();

        // act
        ResponseEntity<Void> result = rest.postForEntity("/api/notification/top/medias", null, Void.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(notificationService, Mockito.times(1)).sendTopMedias();
    }

    @Test
    void checkMetricsTest() {
        // assume
        Mockito.doNothing().when(metricService).checkMetrics();

        // act
        ResponseEntity<Void> result = rest.postForEntity("/api/notification/monitor", null, Void.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Mockito.verify(metricService, Mockito.times(1)).checkMetrics();
    }
}
