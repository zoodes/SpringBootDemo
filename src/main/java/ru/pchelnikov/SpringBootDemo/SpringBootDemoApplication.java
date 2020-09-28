package ru.pchelnikov.SpringBootDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = "ru.pchelnikov.SpringBootDemo")
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		log.info("Main method starts...");

		log.info("Creating model1 via @NoArgsConstructor...");
		firstModel();

		log.info("Creating model2 via @AllAgrsConstructor...");
		secondModel();

		log.info("Creating model3 via @Builder...");
		thirdModel();

		log.info("Creating model4 as ChildModel via @RequiredArgsConstructor");
		fourthModel();

		log.info("Enough with children games, starting the SpringBoot app");
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

	private static void fourthModel() {
		ChildModel model4 = new ChildModel(24);
		model4.setFio("Test test test");
		model4.setMale(false);
		log.debug("model's FIO is {}", model4.getFio());
		log.debug("model is male? {}", model4.isMale());
		log.debug("model's phone is {}", model4.getPhone());
		log.debug("model's age is {}", model4.getAge());
	}

	private static void thirdModel() {
		Model model3 = Model.builder()
				.fio("Karenina Anna Vladimirovna")
				.isMale(false)
				.phone("0234502935")
				.build();
		modelInfo(model3);
	}

	private static void secondModel() {
		Model model2 = new Model("Petrov Petr Petrovich", true, "453663453");
		modelInfo(model2);
	}

	private static void firstModel() {
		Model model1 = new Model();
		model1.setFio("Ivanov Ivan Ivanovich");
		model1.setMale(true);
		model1.setPhone("12356");
		modelInfo(model1);
	}

	private static void modelInfo(Model model) {
		log.debug("model's FIO is {}", model.getFio());
		log.debug("model is male? {}", model.isMale());
		log.debug("model's phone is {}", model.getPhone());
	}

}
