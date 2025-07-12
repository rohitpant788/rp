package com.rohit.recon.recon;

import com.rohit.recon.recon.config.ScraperUrlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ScraperUrlProperties.class)
public class ReconApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReconApplication.class, args);
	}

}
