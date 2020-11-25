package com.garlicode.garlicodemarket.services;

import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UtilsService {

    @Autowired
    private CorporateService corporateService;

    public void uploadTickers(String[] tickers)  {
        for (String ticker : tickers) {
            Optional<Corporate> corporate = this.corporateService.getCorporateByTicker(ticker);
            if (corporate.isEmpty()) {
                try {
                    this.corporateService.addNewCorporate(ticker);
                } catch (Exception e) {
                    // TO ADD LOGS LATER
                }
            }
        }
    }

    public boolean isTickersFileValid(String content, int lines) {
        Pattern pattern = Pattern.compile("(^[a-zA-Z0-9.-]+(\\n\\r)?)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);

        return matcher.results().count() == lines;
    }

}
