package ru.pchelnikov.SpringBootDemo.App.Clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.pchelnikov.SpringBootDemo.App.DTOs.MockServerUserDTO;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IMockServerServiceClient;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class MockServerServiceClient implements IMockServerServiceClient {

    RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
    }

    @Override
    public void create(MockServerUserDTO mockServerUserDTO) {
        //TODO
    }

    @Override
    public MockServerUserDTO read(String id) {
        ResponseEntity<MockServerUserDTO> response =
                restTemplate.getForEntity(
                        "https://serene-coast-56441.herokuapp.com/api/users/" + id,
                        MockServerUserDTO.class);
        return response.getBody();
    }

    @Override
    public void update(MockServerUserDTO mockServerUserDTO) {
        //TODO
    }

    @Override
    public void delete(String id) {
        //TODO
    }

    @Override
    public List<MockServerUserDTO> readAll() {
        MockServerUserDTO[] mockServerUserDTOS = new MockServerUserDTO[0];
        try {
            ResponseEntity<MockServerUserDTO[]> response = restTemplate.getForEntity(
                            "https://serene-coast-56441.herokuapp.com/api/users/",
                            MockServerUserDTO[].class);
            mockServerUserDTOS = response.getBody();
        } catch (RestClientException e) {
//            e.printStackTrace();
//            throw new MockServerException("No users have been found on MockServer!");
            log.error("No users have been found on MockServer. Check if it has any!");
        }
//        if (mockServerUserDTOS == null) throw new MockServerException("User list returned empty!");
        return Arrays.asList(mockServerUserDTOS);
    }

    @Override
    public boolean hasUser(String phone) {
        boolean result = false;
        List<MockServerUserDTO> mockServerUserDTOS = readAll();
        for (MockServerUserDTO mockServerUserDTO : mockServerUserDTOS) {
            if (phone.equals(mockServerUserDTO.phone)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
