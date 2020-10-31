package ru.pchelnikov.SpringBootDemo.ServicesInterfaces;

import ru.pchelnikov.SpringBootDemo.App.DTOs.MockServerUserDTO;

import java.util.List;
import java.util.UUID;

public interface IMockServerServiceClient {
    void create(MockServerUserDTO mockServerUserDTO);
    MockServerUserDTO read(UUID id);
    void update(MockServerUserDTO mockServerUserDTO);
    void delete(UUID id);
    List<MockServerUserDTO> readAll();
    boolean hasUser(String phone);
    boolean hasUser(UUID id);
}
