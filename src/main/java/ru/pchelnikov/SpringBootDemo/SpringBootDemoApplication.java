package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.pchelnikov.SpringBootDemo.Domain.Repositories.DBConnectionFactory;

import java.sql.SQLException;

@Slf4j
@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")
public class SpringBootDemoApplication {

	public static void main(String[] args) throws SQLException {
		log.info("Initializing TelegramBot");
		ApiContextInitializer.init();

		log.info("Starting the SpringBoot application");
		SpringApplication.run(SpringBootDemoApplication.class, args);

		DBConnectionFactory factory = new DBConnectionFactory();
		log.debug("User list in DB: {}", factory.process());

	}
}
