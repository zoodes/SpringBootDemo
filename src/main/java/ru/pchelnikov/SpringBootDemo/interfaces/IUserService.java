package ru.pchelnikov.SpringBootDemo.interfaces;

import ru.pchelnikov.SpringBootDemo.domain.dtos.UserDTO;
import ru.pchelnikov.SpringBootDemo.domain.services.entities.User;

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
