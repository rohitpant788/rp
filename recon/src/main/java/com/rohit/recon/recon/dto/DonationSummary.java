package com.rohit.recon.recon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DonationSummary {
    private String description;
    private String count;
    private String totalValue;
}
