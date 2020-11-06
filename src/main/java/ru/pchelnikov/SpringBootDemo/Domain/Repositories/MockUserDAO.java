package ru.pchelnikov.SpringBootDemo.Domain.Repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IUserDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@ConditionalOnProperty(value = "useMockUserDAO", havingValue = "true")
public class MockUserDAO implements IUserDAO {
    private static final Map<Long, User> CHAT_ID_TO_USER = new HashMap<>();

    @Override
    public void create(User user) {
        CHAT_ID_TO_USER.put(user.getChatId(), user);
    }

    @Override
    public User read(Long chatId) {
        return CHAT_ID_TO_USER.get(chatId);
    }

    @Override
    public void update(User user) {
        CHAT_ID_TO_USER.replace(user.getChatId(), user);
    }

    @Override
    public void delete(Long chatId) {
        CHAT_ID_TO_USER.remove(chatId);
    }

    @Override
    public boolean hasUser(Long chatId) {
        return CHAT_ID_TO_USER.containsKey(chatId);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(CHAT_ID_TO_USER.values());
    }

}
