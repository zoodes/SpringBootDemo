package ru.pchelnikov.SpringBootDemo.Services;

import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Entities.User;

import java.util.List;

public interface IUserService {
    void createUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long chatId);
    void deleteUser(String phone);
    User getUser(Long chatId);
    User getUser(String phone);
    boolean hasUser(Long chatId);
    List<User> getAllUsers();
}
