package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.pchelnikov.SpringBootDemo.Domain.Repositories.DBConnectionFactory;
import ru.pchelnikov.SpringBootDemo.Domain.Repositories.UserDAO;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IUserDAO;

import java.sql.SQLException;
import java.util.Date;

@Slf4j
@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")
public class SpringBootDemoApplication {

	public static void main(String[] args) throws SQLException {
		log.info("Initializing TelegramBot");
		ApiContextInitializer.init();

		log.info("Starting the SpringBoot application");
//		SpringApplication.run(SpringBootDemoApplication.class, args);

		ApplicationContext ctx = SpringApplication.run(SpringBootDemoApplication.class, args);

//		DBConnectionFactory factory = new DBConnectionFactory();
//		log.debug("User list in DB: {}", factory.process());

		IUserDAO userDAO = ctx.getBean(IUserDAO.class);
		log.debug("getAllUsers(): {}", userDAO.getAllUsers());
		log.debug("getUser() with chatId=123456: {}", userDAO.read(123456L));
		log.debug("hasUser() with existing user: {}", userDAO.hasUser(123456L));
		log.debug("hasUser() with non-existing user: {}", userDAO.hasUser(12345678L));

		Long randomChatId = Math.round(100000 * Math.random());
		User user = User.builder()
				.birthDate(new Date())
				.chatId(randomChatId)
				.firstName("Ivan")
				.lastName("Ivanov")
				.userName("ivanushko")
				.phone(randomChatId.toString())
				.build();
		log.debug("create() user: {}", user);
		userDAO.create(user);
		log.debug("has new user with chatId " + randomChatId + ": {}", userDAO.hasUser(randomChatId));
		log.debug("read(): {}", userDAO.read(randomChatId));
		user.setPhone("not_available");
		user.setLastName(null);
		log.debug("now update to user: {}", user);
		userDAO.update(user);
		log.debug("read(): {}", userDAO.read(randomChatId));
		log.debug("now delete the user...");
		userDAO.delete(randomChatId);
		log.debug("has new user with chatId " + randomChatId + ": {}", userDAO.hasUser(randomChatId));
		userDAO.delete(12832L);
	}
}
