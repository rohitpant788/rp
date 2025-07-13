package com.rohit.recon.recon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "donation_search_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationSearchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "detail_link", unique = true)
    private String detailLink;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "register")
    private String register;

    @Column(name = "campaigning_name")
    private String campaigningName;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "value")
    private String value;

    @Column(name = "accepted_date")
    private String acceptedDate;

    @Column(name = "received_by")
    private String receivedBy;

    @Column(name = "donor_name")
    private String donorName;

    @Column(name = "reported_under_6212")
    private String reportedUnder6212;

    @Column(name = "is_sponsorship")
    private String isSponsorship;

    @Column(name = "donor_status")
    private String donorStatus;

    @Column(name = "is_irish_source")
    private String isIrishSource;

    @Column(name = "regulated_donee_type")
    private String regulatedDoneeType;

    @Column(name = "company_reg_no")
    private String companyRegNo;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "donation_type")
    private String donationType;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
