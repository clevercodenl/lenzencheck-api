package com.lenzencheckapi.LenzencheckAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LenzencheckApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(LenzencheckApiApplication.class, args);
	}
}
