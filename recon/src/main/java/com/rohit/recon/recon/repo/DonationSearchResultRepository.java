package com.rohit.recon.recon.repo;

import com.rohit.recon.recon.domain.DonationSearchResult;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DonationSearchResultRepository extends JpaRepository<DonationSearchResult, Long> {

    /**
     * You call insertIgnoreDuplicates() for each new record without needing to check for existence manually.
     * This method will nsure that this avoids duplicates.
     * @param d
     */
    @Modifying
    @Transactional
    @Query(
            value = """
          INSERT INTO donation_search_result (
              detail_link, entity_name, register, campaigning_name, entity_type,
              value, accepted_date, received_by, donor_name, reported_under_6212,
              is_sponsorship, donor_status, is_irish_source, regulated_donee_type,
              company_reg_no, postcode, donation_type, created_at
          )
          VALUES (
              :#{#d.detailLink}, :#{#d.entityName}, :#{#d.register}, :#{#d.campaigningName}, :#{#d.entityType},
              :#{#d.value}, :#{#d.acceptedDate}, :#{#d.receivedBy}, :#{#d.donorName}, :#{#d.reportedUnder6212},
              :#{#d.isSponsorship}, :#{#d.donorStatus}, :#{#d.isIrishSource}, :#{#d.regulatedDoneeType},
              :#{#d.companyRegNo}, :#{#d.postcode}, :#{#d.donationType}, CURRENT_TIMESTAMP
          )
          ON CONFLICT (detail_link) DO NOTHING
          """,
            nativeQuery = true
    )
    void insertIgnoreDuplicates(@Param("d") DonationSearchResult d);
}
