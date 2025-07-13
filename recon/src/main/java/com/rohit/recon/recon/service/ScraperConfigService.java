package com.rohit.recon.recon.service;

import com.rohit.recon.recon.domain.ScraperConfig;
import com.rohit.recon.recon.repo.ScraperConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ScraperConfigService {

    private final ScraperConfigRepository configRepo;

    @Value("${scraper.url}")
    private String defaultUrl;

    private static final String SCRAPER_URL_KEY = "SCRAPER_URL";

    public String getEffectiveUrl() {
        return configRepo.findByKey(SCRAPER_URL_KEY)
                .map(ScraperConfig::getValue)
                .filter(StringUtils::hasText)
                .orElse(defaultUrl);
    }

/*    public void updateUrl(String newUrl) {
        ScraperConfig config = new ScraperConfig();
        config.setKey(SCRAPER_URL_KEY);
        config.setValue(newUrl);
        configRepo.save(config);
    }*/

    public void updateUrl(String key, String value) {
        // Update in DB based on key
        ScraperConfig config = new ScraperConfig();
        config.setKey(key);
        config.setValue(value);
        configRepo.save(config);
    }

    public String getCurrentDbUrl() {
        return configRepo.findByKey(SCRAPER_URL_KEY).map(ScraperConfig::getValue).orElse(null);
    }
}
