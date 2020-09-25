package ru.pchelnikov.SpringBootDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")

public class SpringBootDemoApplication {
	private final static Logger logger = LoggerFactory.getLogger(SpringBootDemoApplication.class);
	public static void main(String[] args) {
		logger.info("main method started");
		SpringApplication.run(SpringBootDemoApplication.class, args);
		logger.info("main method ended");
	}

}
