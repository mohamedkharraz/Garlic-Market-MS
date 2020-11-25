package com.garlicode.garlicodemarket.controllers;

import com.garlicode.garlicodemarket.data.jpa.entity.Industry;
import com.garlicode.garlicodemarket.services.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/industries")
@CrossOrigin("*")
public class IndustryController {

    @Autowired
    private IndustryService service;

    @GetMapping
    public ResponseEntity<List<Industry>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }
}
