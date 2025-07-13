package com.rohit.recon.recon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScraperConfigRequestDto {
    private String key;
    private String value;
}
