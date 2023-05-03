package com.emanuel.starterlibrary.services;

import com.emanuel.starterlibrary.exceptions.SanitizationException;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Service
public class SanitizationService {
    @SneakyThrows
    public String sanitizeString(@NotBlank @NotEmpty String input){
        try {
            Safelist safelist = Safelist.basic();
            Cleaner cleaner = new Cleaner(safelist);
            return cleaner.clean(Jsoup.parse(input)).text();
        } catch (Exception e){
            String errorMessage = "Could not sanitize the input. " + e;
            throw new SanitizationException(errorMessage);
        }
    }
}
