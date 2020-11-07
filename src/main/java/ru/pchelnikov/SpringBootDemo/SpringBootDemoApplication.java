package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.pchelnikov.SpringBootDemo.Domain.Repositories.UserCrudRepository;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;

import java.util.Date;

@Slf4j
@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		log.info("Initializing TelegramBot");
		ApiContextInitializer.init();

		log.info("Starting the SpringBoot application");
		//		SpringApplication.run(SpringBootDemoApplication.class, args);

		ApplicationContext ctx = SpringApplication.run(SpringBootDemoApplication.class, args);
		UserCrudRepository userCrudRepository = ctx.getBean(UserCrudRepository.class);

		Long randomChatId = Math.round(100000 * Math.random());
		User user = User.builder()
				.birthDate(new Date())
				.chatId(randomChatId)
				.firstName("Ivan")
				.lastName("Ivanov")
				.userName("ivanushko")
				.phone(randomChatId.toString())
				.build();

		userCrudRepository.save(user);
	}
}
