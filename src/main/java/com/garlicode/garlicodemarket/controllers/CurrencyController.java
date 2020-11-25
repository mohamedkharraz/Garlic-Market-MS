package com.garlicode.garlicodemarket.controllers;

import com.garlicode.garlicodemarket.data.jpa.entity.Currency;
import com.garlicode.garlicodemarket.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/currencies")
@CrossOrigin(origins = "*")
public class CurrencyController {

    @Autowired
    private CurrencyService service;

    @GetMapping
    public ResponseEntity<List<Currency>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }
}
