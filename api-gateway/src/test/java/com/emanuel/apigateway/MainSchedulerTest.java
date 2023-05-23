package com.emanuel.apigateway;

import com.emanuel.apigateway.MainScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainSchedulerTest {

    @Mock
    WebClient.Builder webClientBuilder;

    @Mock
    WebClient webClient;

    @Mock
    WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    @InjectMocks
    MainScheduler mainScheduler;

    @Test
    void testSendActuatorMetrics() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        mainScheduler.sendActuatorMetrics();

        verify(webClientBuilder, times(1)).build();
        verify(webClient, times(1)).post();
        verify(requestBodyUriSpec, times(1)).uri("http://notification-service/api/notification/actuator");
        verify(responseSpec, times(1)).bodyToMono(Void.class);
    }

    @Test
    void testSendTopMedias() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        mainScheduler.sendTopMedias();

        verify(webClientBuilder, times(1)).build();
        verify(webClient, times(1)).post();
        verify(requestBodyUriSpec, times(1)).uri("http://notification-service/api/notification/top/medias");
        verify(responseSpec, times(1)).bodyToMono(Void.class);
    }

    @Test
    void testCheckMetrics() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        mainScheduler.checkMetrics();

        verify(webClientBuilder, times(1)).build();
        verify(webClient, times(1)).post();
        verify(requestBodyUriSpec, times(1)).uri("http://notification-service/api/notification/monitor");
        verify(responseSpec, times(1)).bodyToMono(Void.class);
    }
}
