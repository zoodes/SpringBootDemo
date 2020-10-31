package ru.pchelnikov.SpringBootDemo.App.Clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import ru.pchelnikov.SpringBootDemo.App.DTOs.UserDto;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IMockServerServiceClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MockServerServiceClient implements IMockServerServiceClient {

    private final RestOperations restTemplate;
    //TODO replace with @value
//    @Value("${mockServer.URL}")
    private String mockServerURL = "https://serene-coast-56441.herokuapp.com/api/";

    public MockServerServiceClient(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void create(UserDto userDto) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<UserDto> request = new HttpEntity<>(userDto); // , headers);
//        restTemplate.postForObject(mockServerURL + "users/", request, UserDto.class);
        log.debug("request: {}", request.toString());
        ResponseEntity<String> responseEntity = restTemplate.exchange(mockServerURL + "users/",
                HttpMethod.POST,
                request,
                String.class);
        log.debug("responseEntity: {}", responseEntity.getBody());
    }

    @Override
    public UserDto read(UUID id) {
        ResponseEntity<UserDto> response =
                restTemplate.getForEntity(
                        mockServerURL + "users/" + id,
                        UserDto.class);
        return response.getBody();
    }

    @Override
    public void update(UserDto userDto) {
        //TODO
    }

    @Override
    public void delete(UUID id) {
        //TODO
    }

    @Override
    public List<UserDto> readAll() {
        UserDto[] userDtos = new UserDto[0];
        try {
            ResponseEntity<UserDto[]> response = restTemplate.getForEntity(
                            mockServerURL + "users/",
                            UserDto[].class);
            userDtos = response.getBody();
        } catch (RestClientException e) {
            log.error("No users have been found on MockServer. Check if it has any!");
            throw e;
        }
        return Arrays.asList(userDtos);
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