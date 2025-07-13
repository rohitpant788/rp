package com.rohit.recon.recon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "recon_data_case")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconDataCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "case_id", unique = true, nullable = false)
    private String caseId; // e.g. REC000000001

    @Column(name = "detail_link", unique = true, nullable = false, columnDefinition = "TEXT")
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

    @Column(name = "process_instance_id")
    private String processInstanceId; // Camunda Process Instance ID

    @Column(name = "status")
    private String status; // e.g. DRAFT

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
