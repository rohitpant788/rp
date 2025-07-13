package com.rohit.recon.recon.controller;

import com.rohit.recon.recon.dto.ScraperConfigRequestDto;
import com.rohit.recon.recon.service.ScraperConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/scraper")
@RequiredArgsConstructor
public class AdminController {

    private final ScraperConfigService scraperConfigService;

    @PostMapping("/update-url")
    public ResponseEntity<String> updateUrl(@RequestBody ScraperConfigRequestDto request) {
        scraperConfigService.updateUrl(request.getKey(), request.getValue());
        return ResponseEntity.ok("Key " + request.getKey() + " updated successfully to: " + request.getValue());
    }

    @GetMapping("/get-url")
    public ResponseEntity<String> getUrl() {
        return ResponseEntity.ok(scraperConfigService.getEffectiveUrl());
    }

}
