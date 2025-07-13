package com.rohit.recon.recon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ScraperConfigRequestDto {
    private String key;
    private String value;
}
