package com.rohit.recon.recon;

import com.rohit.recon.recon.config.ScraperUrlProperties;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ScraperUrlProperties.class)
@EnableProcessApplication
public class ReconApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReconApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ReconApplication.class);
	}
}
