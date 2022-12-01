package com.testspring.enseirb.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PremierTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PremierTestApplication.class, args);
	}

}
