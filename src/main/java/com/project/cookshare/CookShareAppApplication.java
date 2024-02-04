package com.project.cookshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CookShareAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookShareAppApplication.class, args);
	}

}