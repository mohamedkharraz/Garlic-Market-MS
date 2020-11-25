package com.garlicode.garlicodemarket.controllers;

import com.garlicode.garlicodemarket.data.jpa.entity.Sector;
import com.garlicode.garlicodemarket.services.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sectors")
@CrossOrigin("*")
public class SectorController {

    @Autowired
    private SectorService service;

    @GetMapping
    public ResponseEntity<List<Sector>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }
}
