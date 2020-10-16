package ru.pchelnikov.SpringBootDemo.Services;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Entities.User;
import ru.pchelnikov.SpringBootDemo.Repositories.IUserDAO;
import ru.pchelnikov.SpringBootDemo.Repositories.UserDAO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UserService implements IUserService {

    private static IUserDAO userDB = new UserDAO();

    @Override
    public void createUser(UserDTO userDTO) {
        User user = getUserFromUserDTO(userDTO);
        if (!userDB.hasUser(user.getChatId())) {
            userDB.create(user);
            log.info("User {} has been added to userList!", user.getUserName());
        } else {
            log.warn("userList already contains user {}!", user.getUserName());
        }
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = getUserFromUserDTO(userDTO);
        userDB.update(user);
    }

    @Override
    public void deleteUser(Long chatId) {
        userDB.delete(chatId);
    }

    @Override
    public User getUser(Long chatId) {
        return userDB.read(chatId);
    }


    @Override
    public boolean hasUser(Long chatId) {
        return userDB.hasUser(chatId);
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }


    private static User getUserFromUserDTO(UserDTO userDTO) {
        User user = new User().builder()
                .chatId(userDTO.chatId)
                .userName(userDTO.userName)
                .firstName(userDTO.firstName)
                .lastName(userDTO.lastName)
                .birthDate(userDTO.birthDate)
                .build();
        return user;
    }
}
