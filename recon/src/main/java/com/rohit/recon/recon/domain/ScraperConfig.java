package com.rohit.recon.recon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "scraper_config")
@Getter
@Setter
public class ScraperConfig {

    @Id
    private String key;
    private String value;
}
