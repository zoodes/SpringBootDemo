package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pchelnikov.SpringBootDemo.Handlers.BotHandler;
import ru.pchelnikov.SpringBootDemo.Handlers.ProxyHandler;

import java.util.ArrayList;

@Slf4j
@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")
public class SpringBootDemoApplication {

	public static void main(String[] args) {
//		log.info("Trying to bypass proxy, if it exists");
//		ProxyHandler.bypassProxy();

//		log.info("Creating application context");
//		ApplicationContext context = new AnnotationConfigApplicationContext("ru.pchelnikov.SpringBootDemo");

		log.info("Launch telegram bot");
		BotHandler.startBot();

//		log.info("Starting the SpringBoot application");
//		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

}
