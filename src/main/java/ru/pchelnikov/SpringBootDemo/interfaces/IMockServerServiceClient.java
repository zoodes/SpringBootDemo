package ru.pchelnikov.SpringBootDemo.interfaces;

import ru.pchelnikov.SpringBootDemo.app.dtos.MockServerUpdateDTO;
import ru.pchelnikov.SpringBootDemo.app.dtos.MockServerUserDTO;

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
