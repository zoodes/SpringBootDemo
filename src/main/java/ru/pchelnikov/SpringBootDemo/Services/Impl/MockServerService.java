package ru.pchelnikov.SpringBootDemo.Services.Impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.pchelnikov.SpringBootDemo.DTOs.MockServerUserDTO;
import ru.pchelnikov.SpringBootDemo.Services.Exceptions.MockServerException;
import ru.pchelnikov.SpringBootDemo.Services.IMockServerService;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class MockServerService implements IMockServerService {

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
        ResponseEntity<MockServerUserDTO[]> response = restTemplate.getForEntity(
                        "https://serene-coast-56441.herokuapp.com/api/users/",
                        MockServerUserDTO[].class);
        MockServerUserDTO[] mockServerUserDTOS = response.getBody();
        if (mockServerUserDTOS == null) throw new MockServerException("User list returned empty!");
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
