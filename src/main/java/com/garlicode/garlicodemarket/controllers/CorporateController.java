package com.garlicode.garlicodemarket.controllers;

import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;
import com.garlicode.garlicodemarket.services.CorporateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CorporateController {

    @Autowired
    private CorporateService service;

    @GetMapping
    private ResponseEntity<List<Corporate>> getCorporates() {
        return ResponseEntity.ok(this.service.findAll());
    }
}
