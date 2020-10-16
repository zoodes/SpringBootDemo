package ru.pchelnikov.SpringBootDemo.Repositories;

import ru.pchelnikov.SpringBootDemo.Entities.User;

import java.util.List;

public interface IUserDAO {
    void create(User user);
    User read(Long chatId);
    void update(User user);
    void delete(Long chatId);
    boolean hasUser(Long chatId);
    List<User> getAllUsers();
}
