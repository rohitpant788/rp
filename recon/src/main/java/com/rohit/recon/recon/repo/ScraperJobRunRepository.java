package com.rohit.recon.recon.repo;

import com.rohit.recon.recon.domain.ScraperJobRun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScraperJobRunRepository extends JpaRepository<ScraperJobRun, Long> {
}
