package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.pchelnikov.SpringBootDemo.App.DTOs.MockServerUserDTO;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IMockServerServiceClient;

import java.util.Date;
import java.util.UUID;

@Slf4j
@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")
public class SpringBootDemoApplication {

//	@Autowired
//	private IMockServerServiceClient mockServerServiceClient;

	public static void main(String[] args) {
		log.info("Initializing TelegramBot");
		ApiContextInitializer.init();

		log.info("Starting the SpringBoot application");
//		SpringApplication.run(SpringBootDemoApplication.class, args);


		ApplicationContext ctx = SpringApplication.run(SpringBootDemoApplication.class, args);

		IMockServerServiceClient mockServerServiceClient = ctx.getBean(IMockServerServiceClient.class);
		log.debug("readAll(): {}", mockServerServiceClient.readAll());
		log.debug("add new user...");
		UUID id = UUID.randomUUID();
		MockServerUserDTO dto = new MockServerUserDTO();
		dto.birthDay = new Date();
		dto.chatId = "123456789";
		dto.firstName = "Ivan";
		dto.middleName = "Ivanovich";
		dto.secondName = "Ivanov";
		dto.id = id;
		dto.male = true;
		dto.phone = "+79641831346";
		UUID newId = mockServerServiceClient.create(dto);
		log.debug("success! new user id is: {}", newId);
		log.debug("now user list is: {}", mockServerServiceClient.readAll());
		log.debug("has user with newId: {}", mockServerServiceClient.hasUser(newId));
		log.debug("read user with newId: {}", mockServerServiceClient.read(newId));
		log.debug("has user with random id: {}", mockServerServiceClient.hasUser(UUID.randomUUID()));
		log.debug("has user with phone +79641831346: {}", mockServerServiceClient.hasUser("+79641831346"));
		log.debug("read non-existing user: {}", mockServerServiceClient.read(UUID.randomUUID()));
	}
}
