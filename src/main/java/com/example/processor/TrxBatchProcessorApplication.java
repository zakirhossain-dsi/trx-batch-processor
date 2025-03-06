package com.example.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrxBatchProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrxBatchProcessorApplication.class, args);
	}

}
