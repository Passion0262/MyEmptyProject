package com.example.kafkatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.kafkatest.controller")
@ComponentScan("com.example.kafkatest.config")
public class KafkatestApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkatestApplication.class, args);
	}

}
