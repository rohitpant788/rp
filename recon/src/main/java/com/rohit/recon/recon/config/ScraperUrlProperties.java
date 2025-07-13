package com.rohit.recon.recon.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "scraper")
public class ScraperUrlProperties {
    private String url;
}
