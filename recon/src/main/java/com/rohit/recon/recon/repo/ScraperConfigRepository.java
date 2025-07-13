package com.rohit.recon.recon.repo;

import com.rohit.recon.recon.domain.ScraperConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScraperConfigRepository extends JpaRepository<ScraperConfig, String> {
    Optional<ScraperConfig> findByKey(String key);
}
