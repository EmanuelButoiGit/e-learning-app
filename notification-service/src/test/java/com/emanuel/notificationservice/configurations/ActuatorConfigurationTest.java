package com.emanuel.notificationservice.configurations;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ActuatorConfigurationTest {

    @Test
    void testMetricsEndpoint() {
        MeterRegistry meterRegistry = mock(MeterRegistry.class);
        ActuatorConfiguration configuration = new ActuatorConfiguration();
        MetricsEndpoint metricsEndpoint = configuration.metricsEndpoint(meterRegistry);
        assertNotNull(metricsEndpoint, "MetricsEndpoint bean should not be null");
    }
}
