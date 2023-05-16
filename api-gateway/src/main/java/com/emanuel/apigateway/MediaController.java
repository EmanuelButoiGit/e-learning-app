package com.emanuel.apigateway;

import com.emanuel.apigateway.proxies.MediaServiceProxy;
import com.emanuel.starterlibrary.dtos.MediaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("media-service/api/media")
public class MediaController {
    private final MediaServiceProxy mediaServiceProxy;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all media files")
    @ApiResponse(responseCode = "200", description = "All media files retrieved")
    public Mono<List<MediaDto>> getAllMedias() {
        return Mono.fromCallable(mediaServiceProxy::getAllMedias)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
