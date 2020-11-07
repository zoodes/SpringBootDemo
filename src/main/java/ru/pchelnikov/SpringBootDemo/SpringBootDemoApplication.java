package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.pchelnikov.SpringBootDemo.App.TelegramHandlers.ReplyHandler;
import ru.pchelnikov.SpringBootDemo.Domain.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;
import ru.pchelnikov.SpringBootDemo.Domain.Services.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
//		UserCrudRepository userCrudRepository = ctx.getBean(UserCrudRepository.class);
//
//		Long randomChatId = Math.round(100000 * Math.random());
//		User user = User.builder()
//				.birthDate(new Date())
//				.chatId(randomChatId)
//				.firstName("Ivan")
//				.lastName("Ivanov")
//				.userName("ivanushko")
//				.phone(randomChatId.toString())
//				.build();
//
//		userCrudRepository.save(user);

//		UserService userService = ctx.getBean(UserService.class);
//		UserDTO userDTO = createRandomUserDTO();
//		Long chatId = userDTO.chatId;
//		log.debug("Creating user with chatID: {}", chatId);
//		userService.createUser(userDTO);
//		log.debug("User db now has users: {}", userService.getAllUsers());
//		log.debug("Has user with chatID {}: {}", chatId, userService.hasUser(chatId));
//		log.debug("Has user with chatID 1: {}", userService.hasUser(1L));
//		User newUser;
//		log.debug("Read user with chatID {}: {}", chatId, newUser = userService.getUser(chatId));
//		log.debug("Update user's birthday to 2000-01-01");
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			newUser.setBirthDate(simpleDateFormat.parse("2000-01-01"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		log.debug("Write update to db...");
//		userService.updateUser(mapUserToUserDTO(newUser));
//		log.debug("Read user with chatID {}: {}", chatId, userService.getUser(chatId));
//		log.debug("Deleting user with chatID {}...", chatId);
//		userService.deleteUser(chatId);
//		log.debug("Has user with chatID {}: {}", chatId, userService.hasUser(chatId));
//
//		userDTO = createRandomUserDTO();
//		chatId = userDTO.chatId;
//		log.debug("Recreating new user with chatID {}...", chatId);
//		userService.createUser(userDTO);
//		log.debug("Has user with chatID {}: {}", chatId, userService.hasUser(chatId));
//		log.debug("Read user with chatID {}: {}", chatId, userService.getUser(chatId));
//		String phone = userDTO.phone;
//		log.debug("Read user with chatID {} by phone {}: {}", chatId, phone, userService.getUser(phone));
//		log.debug("Delete user with chatID {} by phone {}...", chatId, phone);
//		userService.deleteUser(phone);
//		log.debug("Has user with chatID {}: {}", chatId, userService.hasUser(chatId));
//
//		log.debug("read non-existent user...");
//		userService.getUser("123L");

		ReplyHandler replyHandler = ctx.getBean(ReplyHandler.class);
		replyHandler.updateUserOnMockServer(1353325014L);
	}

	private static UserDTO createRandomUserDTO() {
		Long randomChatId = Math.round(100000 * Math.random());
		return UserDTO.builder()
				.birthDate(new Date())
				.chatId(randomChatId)
				.firstName("firstName" + randomChatId)
				.lastName("lastName" + randomChatId)
				.userName("userName" + randomChatId)
				.phone(randomChatId.toString() + randomChatId.toString())
				.build();
	}

	private static UserDTO mapUserToUserDTO(User user) {
		return UserDTO.builder()
				.chatId(user.getChatId())
				.phone(user.getPhone())
				.birthDate(user.getBirthDate())
				.lastName(user.getLastName())
				.firstName(user.getFirstName())
				.userName(user.getUserName())
				.build();
	}
}
