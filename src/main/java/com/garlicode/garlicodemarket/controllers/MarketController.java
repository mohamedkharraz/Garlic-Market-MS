package com.garlicode.garlicodemarket.controllers;

import com.garlicode.garlicodemarket.data.jpa.pojo.CorporateData;
import com.garlicode.garlicodemarket.providers.DataProvider;
import com.garlicode.garlicodemarket.providers.YahooProvider;
import com.garlicode.garlicodemarket.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "*")
public class MarketController {

    @Autowired
    private MarketService service;

    @GetMapping("/{ticker}")
    private ResponseEntity<CorporateData> getCompanyStats(@PathVariable String ticker) {
        CorporateData result = this.service.getCorporateData(ticker);
        return ResponseEntity.ok(result);
    }

}
