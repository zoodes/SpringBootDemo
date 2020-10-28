package ru.pchelnikov.SpringBootDemo.Services;

import ru.pchelnikov.SpringBootDemo.DTOs.MockServerUserDTO;

import java.util.List;

public interface IMockServerService {
    void create(MockServerUserDTO mockServerUserDTO);
    MockServerUserDTO read(String id);
    void update(MockServerUserDTO mockServerUserDTO);
    void delete(String id);
    List<MockServerUserDTO> readAll();
    boolean hasUser(String phone);
}
