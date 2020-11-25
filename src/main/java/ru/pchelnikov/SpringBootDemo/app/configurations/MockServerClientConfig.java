package ru.pchelnikov.SpringBootDemo.app.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.pchelnikov.SpringBootDemo.app.clients.MockServerMockServiceClient;
import ru.pchelnikov.SpringBootDemo.app.clients.MockServerServiceClient;
import ru.pchelnikov.SpringBootDemo.interfaces.IMockServerServiceClient;

@Configuration
public class MockServerClientConfig {
    @Bean
    public RestOperations restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
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
