package ru.pchelnikov.SpringBootDemo.ServicesInterfaces;

import ru.pchelnikov.SpringBootDemo.App.DTOs.UserDto;

import java.util.List;
import java.util.UUID;

public interface IMockServerServiceClient {
    void create(UserDto userDto);
    UserDto read(UUID id);
    void update(UserDto userDto);
    void delete(UUID id);
    List<UserDto> readAll();
    boolean hasUser(String phone);
    boolean hasUser(UUID id);
}
