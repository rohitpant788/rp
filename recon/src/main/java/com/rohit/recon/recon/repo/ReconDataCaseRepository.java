package com.rohit.recon.recon.repo;

import com.rohit.recon.recon.domain.ReconDataCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReconDataCaseRepository extends JpaRepository<ReconDataCase, Long> {
    boolean existsByDetailLink(String detailLink);
}
