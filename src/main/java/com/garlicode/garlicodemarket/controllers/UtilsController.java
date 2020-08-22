package com.garlicode.garlicodemarket.controllers;

import com.garlicode.garlicodemarket.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/utils")
@CrossOrigin(origins = "*")
public class UtilsController {

    @Autowired
    private UtilsService service;

    @PostMapping("/tickers-upload")
    private ResponseEntity<Optional<Boolean>> uploadTickers(@RequestPart MultipartFile file) throws InterruptedException {
        try {
            String content = new String(file.getBytes()).trim();
            String[] tickers = content.split("[\n\r]");

            if (!this.service.isTickersFileValid(content, tickers.length))
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid File Format");
            this.service.uploadTickers(tickers);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(Optional.of(true));
    }
}
