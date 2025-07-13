package com.rohit.recon.recon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "scraper_job_run")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScraperJobRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobType; // e.g. "donation-summary", "search-results"

    private LocalDateTime runTime;

    private boolean success;

    private int recordCount;

    private String errorMessage; // Nullable, only if failed
}
