package ru.pchelnikov.SpringBootDemo.App.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MockServerClientConfig {
    @Bean
    public RestOperations restTemplate() {
        return new RestTemplate();
    }
}
