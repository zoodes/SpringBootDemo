package ru.pchelnikov.SpringBootDemo.ServicesInterfaces;

import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;

import java.util.List;

public interface IUserDAO {
    void create(User user);
    User read(Long chatId);
    void update(User user);
    void delete(Long chatId);
    boolean hasUser(Long chatId);
    List<User> getAllUsers();
}
