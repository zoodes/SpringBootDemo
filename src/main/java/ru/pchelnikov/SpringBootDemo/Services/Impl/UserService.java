package ru.pchelnikov.SpringBootDemo.Services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Entities.User;
import ru.pchelnikov.SpringBootDemo.Repositories.IUserDAO;
import ru.pchelnikov.SpringBootDemo.Repositories.UserDAO;
import ru.pchelnikov.SpringBootDemo.Services.IUserService;

import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    private static final IUserDAO userDB = new UserDAO();

    @Override
    public void createUser(UserDTO userDTO) {
        User user = getUserFromUserDTO(userDTO);
        if (!userDB.hasUser(user.getChatId())) {
            userDB.create(user);
            log.info("User {} has been added to userList!", user.getUserName());
        } else {
            log.warn("userList already contains user {}!", user.getUserName());
        }
        log.info("Userlist now contains: {}", getAllUsers());
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = getUserFromUserDTO(userDTO);
        userDB.update(user);
        log.info("User {} has been updated!", user.getUserName());
        log.info("Userlist now contains: {}", getAllUsers());
    }

    @Override
    public void deleteUser(Long chatId) {
        User user = userDB.read(chatId);
        userDB.delete(chatId);
        log.info("User {} has been deleted!", user.getUserName());
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
        return userDB.getAllUsers();
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
