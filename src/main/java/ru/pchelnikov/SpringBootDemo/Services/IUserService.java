package ru.pchelnikov.SpringBootDemo.Services;

import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Entities.User;

import java.util.List;

public interface IUserService {
    void createUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long chatId);
    User getUser(Long chatId);
    boolean hasUser(Long chatId);
    List<User> getAllUsers();
}
