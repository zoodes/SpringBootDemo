package ru.pchelnikov.SpringBootDemo.ServicesInterfaces;

import ru.pchelnikov.SpringBootDemo.App.DTOs.MockServerUserDTO;

import java.util.List;

public interface IMockServerServiceClient {
    void create(MockServerUserDTO mockServerUserDTO);
    MockServerUserDTO read(String id);
    void update(MockServerUserDTO mockServerUserDTO);
    void delete(String id);
    List<MockServerUserDTO> readAll();
    boolean hasUser(String phone);
}
