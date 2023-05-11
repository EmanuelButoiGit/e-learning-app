package com.emanuel.mediaservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QualityService {

    @SuppressWarnings("squid:S3776")
    public Integer calculateResolutionQuality(Integer width, Integer height) {
        if (width <= 144 && height <= 144) {
            log.info("This media resolution quality is 144p");
            return height;
        } else if (width <= 360 && height <= 360) {
            log.info("This media resolution quality is 360p");
            return height;
        } else if (width <= 640 && height <= 480) {
            log.info("This media resolution quality is SD (480p)");
            return height;
        } else if (width <= 1280 && height <= 720) {
            log.info("This media resolution quality is HD (720p)");
            return height;
        } else if (width <= 1920 && height <= 1080) {
            log.info("This media resolution quality is Full HD (1080p)");
            return height;
        } else if (width <= 2560 && height <= 1440) {
            log.info("This media resolution quality is 2K (1440p)");
            return height;
        } else if (width <= 3840 && height <= 2160) {
            log.info("This media resolution quality is 4K or Ultra HD (2160p)");
            return height;
        } else if (width <= 7680 && height <= 4320) {
            log.info("This media resolution quality is 8K or Full Ultra HD (4320p)");
            return height;
        } else {
            log.info("This media resolution standard is UNKNOWN");
            return 0;
        }
    }
}
