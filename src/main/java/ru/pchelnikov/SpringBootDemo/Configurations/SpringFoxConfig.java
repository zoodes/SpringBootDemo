package ru.pchelnikov.SpringBootDemo.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.pchelnikov.SpringBootDemo"))
                .build();
    }

    private ApiInfo apiInfo = new ApiInfo(
            "@pchel_study_bot",
            "Pchelnikov Telegram bot REST API documentation",
            "0.0.1",
            "",
            new Contact(
                    "Pchelnikov",
                    "https://blooming-ridge-37758.herokuapp.com/",
                    "pchelnikoviv@yandex.ru"
            ),
            "",
            "",
            new ArrayList());
}