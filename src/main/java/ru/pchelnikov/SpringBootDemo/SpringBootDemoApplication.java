package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.pchelnikov.SpringBootDemo.Handlers.BotHandler;
import ru.pchelnikov.SpringBootDemo.Handlers.ProxyHandler;

@Slf4j
@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		log.info("Trying to bypass proxy, if it exists");
		ProxyHandler.bypassProxy();

		log.info("Launch telegram bot");
		BotHandler.startBot();

		log.info("Starting the SpringBoot application");
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

}
