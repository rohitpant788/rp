package com.rohit.recon.recon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationSearchResultDto {
    private String detailLink;
    private String entityName;
    private String register;
    private String campaigningName;
    private String entityType;
    private String value;
    private String acceptedDate;
    private String receivedBy;
    private String donorName;
    private String reportedUnder6212;
    private String isSponsorship;
    private String donorStatus;
    private String isIrishSource;
    private String regulatedDoneeType;
    private String companyRegNo;
    private String postcode;
    private String donationType;
}


