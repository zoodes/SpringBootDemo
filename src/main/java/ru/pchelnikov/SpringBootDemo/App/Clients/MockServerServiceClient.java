package ru.pchelnikov.SpringBootDemo.App.Clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import ru.pchelnikov.SpringBootDemo.App.DTOs.MockServerUserDTO;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IMockServerServiceClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class MockServerServiceClient implements IMockServerServiceClient {
    @Autowired
    private RestOperations restTemplate;
    @Value("${mockServer.URL}")
    private String mockServerURL;

    @Override
    public UUID create(MockServerUserDTO mockServerUserDTO) {
        HttpEntity<MockServerUserDTO> request = new HttpEntity<>(mockServerUserDTO);
        ResponseEntity<String> responseEntity = restTemplate.exchange(mockServerURL + "users/",
                HttpMethod.POST,
                request,
                String.class);
        return UUID.fromString(responseEntity.getBody().replaceAll("\"", ""));
    }

    @Override
    public MockServerUserDTO read(UUID id) {
        ResponseEntity<MockServerUserDTO> response =
                restTemplate.getForEntity(
                        mockServerURL + "users/" + id,
                        MockServerUserDTO.class);
        return response.getBody();
    }

    @Override
    public List<MockServerUserDTO> readAll() {
        MockServerUserDTO[] mockServerUserDTOS = new MockServerUserDTO[0];
        try {
            ResponseEntity<MockServerUserDTO[]> response = restTemplate.getForEntity(
                            mockServerURL + "users/",
                            MockServerUserDTO[].class);
            mockServerUserDTOS = response.getBody();
        } catch (RestClientException e) {
            log.error("No users have been found on MockServer. Check if it has any!");
        }
        return Arrays.asList(mockServerUserDTOS);
    }

    @Override
    public boolean hasUser(String phone) {
        return readAll().stream()
                .anyMatch(dto -> phone.equals(dto.phone));
    }

    @Override
    public boolean hasUser(UUID id) {
        try {
            read(id);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}