package com.emanuel.notificationservice;

import com.emanuel.notificationservice.controllers.NotificationController;
import com.emanuel.notificationservice.services.MetricService;
import com.emanuel.notificationservice.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest {
    @Mock
    private NotificationService notificationService;

    @Mock
    private MetricService metricService;

    @InjectMocks
    private NotificationController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testSendActuatorMetrics() throws Exception {
        mockMvc.perform(post("/api/notification/actuator").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(notificationService).sendActuatorMetrics();
    }

    @Test
    void testSendAlert() throws Exception {
        mockMvc.perform(post("/api/notification/alert").contentType(MediaType.APPLICATION_JSON)
                        .param("alert", "Test Alert"))
                .andExpect(status().isOk());
        verify(notificationService).sendAlert("Test Alert");
    }

    @Test
    void testSendNewMediaNotification() throws Exception {
        mockMvc.perform(post("/api/notification/new/media").contentType(MediaType.APPLICATION_JSON)
                        .param("newMedia", "Test Media"))
                .andExpect(status().isOk());
        verify(notificationService).sendNewMediaNotification("Test Media");
    }

    @Test
    void testSendTopMedias() throws Exception {
        mockMvc.perform(post("/api/notification/top/medias").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(notificationService).sendTopMedias();
    }

    @Test
    void testCheckMetrics() throws Exception {
        mockMvc.perform(post("/api/notification/monitor").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(metricService).checkMetrics();
    }
}

