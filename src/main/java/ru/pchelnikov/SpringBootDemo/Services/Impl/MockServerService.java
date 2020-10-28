package ru.pchelnikov.SpringBootDemo.Services.Impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.pchelnikov.SpringBootDemo.DTOs.MockServerUserDTO;
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

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<MockServerUserDTO> readAll() {
        ResponseEntity<MockServerUserDTO[]> response = restTemplate.getForEntity(
                        "https://serene-coast-56441.herokuapp.com/api/users/",
                        MockServerUserDTO[].class);
        MockServerUserDTO[] mockServerUserDTOS = response.getBody();
        return Arrays.asList(mockServerUserDTOS);
    }
}
