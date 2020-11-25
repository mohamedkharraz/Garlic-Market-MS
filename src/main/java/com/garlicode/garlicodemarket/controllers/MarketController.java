package com.garlicode.garlicodemarket.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.garlicode.garlicodemarket.data.jpa.pojo.CorporateData;
import com.garlicode.garlicodemarket.services.MarketService;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "*")
public class MarketController {

    @Autowired
    private MarketService service;

    @GetMapping("/{ticker}")
    private ResponseEntity<CorporateData> getCompanyStats(@PathVariable String ticker) {
        CorporateData result = null;
        try {
            result = this.service.getCorporateData(ticker);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not Add new Corporate " + ticker);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/garlic-screener")
    private ResponseEntity<List<CorporateData>> screen(@RequestBody String conditions){
        return ResponseEntity.ok(this.service.screenTheMarket(conditions));
    }

}
