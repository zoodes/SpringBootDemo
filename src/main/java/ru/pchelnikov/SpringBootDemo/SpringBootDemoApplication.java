package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		int count = 0;
		log.trace("this is TRACE message #{}", ++count);
		log.debug("this is DEBUG message #{}", ++count);
		log.info("this is INFO message #{}", ++count);
		log.warn("this is WARN message #{}", ++count);
		log.error("this is ERROR message #{}", ++count, new Exception("This is an Exception"));

		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

}
