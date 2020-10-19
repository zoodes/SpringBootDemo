package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.pchelnikov.SpringBootDemo.Services.Impl.UserService;
import ru.pchelnikov.SpringBootDemo.TelegramHandlers.BotHandler;

@Slf4j
@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		log.info("Initializing TelegramBot");
		ApiContextInitializer.init();

		log.info("Starting the SpringBoot application");
		SpringApplication.run(SpringBootDemoApplication.class, args);

		//TODO: make dependency injections via Spring on whole code
	}

}
