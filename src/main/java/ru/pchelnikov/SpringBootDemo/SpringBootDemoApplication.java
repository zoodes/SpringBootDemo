package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.pchelnikov.SpringBootDemo.App.Clients.MockServerServiceClient;
import ru.pchelnikov.SpringBootDemo.App.DTOs.UserDto;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IMockServerServiceClient;

import java.util.Date;
import java.util.UUID;

@Slf4j
@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")
public class SpringBootDemoApplication {
	@Autowired
	IMockServerServiceClient mockServerServiceClient;

	public static void main(String[] args) {
		log.info("Initializing TelegramBot");
		ApiContextInitializer.init();

		log.info("Starting the SpringBoot application");
		SpringApplication.run(SpringBootDemoApplication.class, args);

		MockServerServiceClient mockServerServiceClient = new MockServerServiceClient(new RestTemplate());
		log.debug("{}", mockServerServiceClient.readAll());
		log.debug("read existing uuid: {}", mockServerServiceClient.read(UUID.fromString("50492e7e-ba21-4904-b04b-fec3134540a5")));
//		log.debug("read non-existing uuid: {}", mockServerServiceClient.read(UUID.fromString("502b11d0-5354-4ff3-adc7-98722b98d214")));
		log.debug("has user with phone \"string\": {}", mockServerServiceClient.hasUser("string"));
		log.debug("has user with phone \"string111\": {}", mockServerServiceClient.hasUser("string111"));
		log.debug("has user with existing UUID: {}", mockServerServiceClient.hasUser(UUID.fromString("50492e7e-ba21-4904-b04b-fec3134540a5")));
		log.debug("has user with non-existing UUID: {}", mockServerServiceClient.hasUser(UUID.fromString("502b11d0-5354-4ff3-adc7-98722b98d214")));
		log.debug("has user with generated-by-me UUID: {}", mockServerServiceClient.hasUser(UUID.fromString("e84cafdf-d31b-4ec1-bf7e-f1714a026804")));

		UUID id = UUID.randomUUID();
		log.debug("generating new user with id {}...", id);
		mockServerServiceClient.create(UserDto.builder()
				.birthDay(new Date())
				.chatId("123456789")
				.firstName("Hello")
				.secondName("Kitty")
				.middleName("123")
				.id(id)
				.male(false)
				.phone(id.toString())
				.build());
		log.debug("is user created? {}", mockServerServiceClient.hasUser(id));
		log.debug("is user with phone==id created? {}", mockServerServiceClient.hasUser(id.toString()));
	}

}
