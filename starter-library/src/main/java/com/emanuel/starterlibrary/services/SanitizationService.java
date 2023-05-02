package com.emanuel.starterlibrary.services;

import com.emanuel.starterlibrary.exceptions.SanitizationException;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class SanitizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SanitizationService.class);
    @SneakyThrows
    public String sanitizeString(@Valid String input){
        try {
            Safelist safelist = Safelist.basic();
            Cleaner cleaner = new Cleaner(safelist);
            return cleaner.clean(Jsoup.parse(input)).text();
        } catch (Exception e){
            String errorMessage = "Could not sanitize the input. " + e.getMessage();
            LOGGER.error(e.getMessage());
            throw new SanitizationException(errorMessage);
        }
    }
}
