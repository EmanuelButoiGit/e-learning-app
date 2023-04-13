package com.emanuel.starterlibrary.services;

import org.jsoup.Jsoup;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

@Service
public class SanitizationService {
    public String sanitizeString(String string){
        Safelist safelist = Safelist.basic();
        Cleaner cleaner = new Cleaner(safelist);
        return cleaner.clean(Jsoup.parse(string)).text();
    }
}
