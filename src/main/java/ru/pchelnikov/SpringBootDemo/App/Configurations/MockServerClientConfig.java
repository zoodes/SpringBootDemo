package ru.pchelnikov.SpringBootDemo.App.Configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.pchelnikov.SpringBootDemo.App.Clients.MockServerMockServiceClient;
import ru.pchelnikov.SpringBootDemo.App.Clients.MockServerServiceClient;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IMockServerServiceClient;

@Configuration
public class MockServerClientConfig {
    @Bean
    public RestOperations restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnProperty(value = "useMockServerClient", havingValue = "true")
    public IMockServerServiceClient useMock() {
        return new MockServerMockServiceClient();
    }

    @Bean
    @ConditionalOnProperty(value = "useMockServerClient", havingValue = "false")
    public IMockServerServiceClient useNoMock() {
        return new MockServerServiceClient();
    }
}
