package ru.pchelnikov.SpringBootDemo.ServicesInterfaces;

import ru.pchelnikov.SpringBootDemo.App.DTOs.MockServerUpdateDTO;
import ru.pchelnikov.SpringBootDemo.App.DTOs.MockServerUserDTO;

import java.util.List;
import java.util.UUID;

public interface IMockServerServiceClient {
    UUID create(MockServerUserDTO mockServerUserDTO);
    MockServerUserDTO read(UUID id);
    MockServerUserDTO read(String phone);
    List<MockServerUserDTO> readAll();
    boolean hasUser(String phone);
    boolean hasUser(UUID id);
    boolean update(UUID id, MockServerUpdateDTO mockServerUpdateDTO);
}
