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

    @Autowired
    private CorporateService service;

    @GetMapping
    private ResponseEntity<List<Corporate>> getCorporates() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{ticker}")
    private ResponseEntity<Corporate> getCorporateByTicker(@PathVariable String ticker){
        Optional<Corporate> corporate = this.service.getCorporateByTicker(ticker);
        if(corporate.isPresent())
            return ResponseEntity.ok(corporate.get());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corporate Not Found");
    }

}
