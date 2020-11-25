package com.garlicode.garlicodemarket.controllers;

import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;
import com.garlicode.garlicodemarket.services.CorporateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/corporates")
@CrossOrigin(origins = "*")
public class CorporateController {

    private final static String NOT_FOUND_ERROR = "Corporate %s Not Found";
    private final static String ALREADY_EXISTS_ERROR = "Corporate %s Already Exists";

    @Autowired
    private CorporateService service;

    @GetMapping
    private ResponseEntity<List<Corporate>> getCorporates() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{ticker}")
    private ResponseEntity<Corporate> getCorporateByTicker(@PathVariable String ticker) {
        Optional<Corporate> corporate = this.service.getCorporateByTicker(ticker);
        if (corporate.isPresent())
            return ResponseEntity.ok(corporate.get());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(NOT_FOUND_ERROR, ticker));
    }

    @PostMapping("/{ticker}")
    private ResponseEntity<Corporate> addNewCorporate(@PathVariable String ticker) {
        Optional<Corporate> corporate = this.service.getCorporateByTicker(ticker);
        if (corporate.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(ALREADY_EXISTS_ERROR, ticker));
        Corporate newCorporate = null;
        try {
            newCorporate = this.service.addNewCorporate(ticker);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(NOT_FOUND_ERROR + " or Data Could Not be Retrieved", ticker));
        }
        return ResponseEntity.ok(newCorporate);
    }

    @PutMapping("/{ticker}")
    private ResponseEntity<Corporate> updateCorporate(@PathVariable String ticker, @RequestBody Corporate newCorporate) {
        Optional<Corporate> corporate = this.service.getCorporateByTicker(ticker);
        if (corporate.isPresent())
            return ResponseEntity.ok(this.service.updateCorporate(corporate.get(), newCorporate));
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(NOT_FOUND_ERROR, ticker));
    }
    
    @GetMapping("/countries")
    private ResponseEntity<List<String>> getDistinctCuntries(){
    	return ResponseEntity.ok(this.service.getDistinctCountries());
    }


}
