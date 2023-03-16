package com.emanuel.mediaservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QualityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QualityService.class);

    public Integer calculateResolutionQuality(Integer width, Integer height) {
        if (width <= 144 && height <= 144) {
            LOGGER.info("This media resolution quality is 144p");
            return height;
        }
        if (width <= 360 && height <= 360) {
            LOGGER.info("This media resolution quality is 360p");
            return height;
        }
        if (width <= 640 && height <= 480) {
            LOGGER.info("This media resolution quality is SD (480p)");
            return height;
        }
        if (width <= 1280 && height <= 720) {
            LOGGER.info("This media resolution quality is HD (720p)");
            return height;
        }
        if (width <= 1920 && height <= 1080) {
            LOGGER.info("This media resolution quality is Full HD (1080p)");
            return height;
        }
        if (width <= 2560 && height <= 1440) {
            LOGGER.info("This media resolution quality is 2K (1440p)");
            return height;
        }
        if (width <= 3840 && height <= 2160) {
            LOGGER.info("This media resolution quality is 4K or Ultra HD (2160p)");
            return height;
        }
        if (width <= 7680 && height <= 4320) {
            LOGGER.info("This media resolution quality is 8K or Full Ultra HD (4320p)");
            return height;
        }
        LOGGER.info("This media resolution standard is UNKNOWN");
        return 0;
    }
}
