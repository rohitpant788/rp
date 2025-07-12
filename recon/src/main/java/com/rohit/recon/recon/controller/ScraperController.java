package com.rohit.recon.recon.controller;

import com.rohit.recon.recon.dto.DonationSearchResultDto;
import com.rohit.recon.recon.dto.DonationSummary;
import com.rohit.recon.recon.service.WebScrapingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scraper")
@AllArgsConstructor
public class ScraperController {

    private final WebScrapingService webScrapingService;

    @GetMapping("/donation-summary")
    public ResponseEntity<List<DonationSummary>> triggerScrape() {
        return ResponseEntity.ok(webScrapingService.getSearchSummary());
    }

    @GetMapping("/search-results")
    public ResponseEntity<List<DonationSearchResultDto>> getSearchResults() {
        List<DonationSearchResultDto> results = webScrapingService.extractSearchResults();
        return ResponseEntity.ok(results);
    }
}
