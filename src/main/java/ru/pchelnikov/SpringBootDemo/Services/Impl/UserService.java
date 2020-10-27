package ru.pchelnikov.SpringBootDemo.Services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Entities.User;
import ru.pchelnikov.SpringBootDemo.Repositories.IUserDAO;
import ru.pchelnikov.SpringBootDemo.Services.Exceptions.UserNotFoundException;
import ru.pchelnikov.SpringBootDemo.Services.IUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserService implements IUserService {

    private final IUserDAO userDB;
    private final Map<String, Long> PHONE_TO_CHAT_ID = new HashMap<>();

    public UserService(IUserDAO userDB) {
        this.userDB = userDB;
    }

    @Override
    public void createUser(UserDTO userDTO) {
        User user = getUserFromUserDTO(userDTO);
        if (!userDB.hasUser(user.getChatId())) {
            userDB.create(user);
            log.info("User {} has been added to userList!", user.getChatId());
        } else {
            log.warn("userList already contains user {}!", user.getChatId());
        }
        log.info("Userlist now contains: {}", getAllUsers());
    }


    @Override
    public void updateUser(UserDTO userDTO) {
        if (!userDB.hasUser(userDTO.chatId)) throw UserNotFoundException.init(userDTO.chatId);
        User oldUser = userDB.read(userDTO.chatId);
        User newUser = User.builder()
                .chatId(userDTO.chatId)
                .userName(userDTO.userName != null ? userDTO.userName : oldUser.getUserName())
                .firstName(userDTO.firstName != null ? userDTO.firstName : oldUser.getFirstName())
                .lastName(userDTO.lastName != null ? userDTO.lastName : oldUser.getLastName())
                .birthDate(userDTO.birthDate != null ? userDTO.birthDate : oldUser.getBirthDate())
                .phone(userDTO.phone != null ? userDTO.phone : oldUser.getPhone())
                .build();
        userDB.update(newUser);

        if (newUser.getPhone() != null) {
            PHONE_TO_CHAT_ID.put(newUser.getPhone(), newUser.getChatId());
        }

        log.info("User {} has been updated!", newUser.getChatId());
        log.info("Userlist now contains: {}", getAllUsers());
    }

    @Override
    public void deleteUser(Long chatId) {
        if (!userDB.hasUser(chatId)) throw UserNotFoundException.init(chatId);
        User user = userDB.read(chatId);
        log.info("User {} is about to be deleted!", user.getUserName());
        userDB.delete(chatId);
        PHONE_TO_CHAT_ID.remove(user.getPhone());
        log.info("Deletion complete!");
    }

    @Override
    public void deleteUser(String phone) {
        if (!PHONE_TO_CHAT_ID.containsKey(phone)) throw UserNotFoundException.init(phone);
        Long chatId = PHONE_TO_CHAT_ID.get(phone);
        deleteUser(chatId);
    }

    @Override
    public User getUser(Long chatId) {
        if (!userDB.hasUser(chatId)) throw UserNotFoundException.init(chatId);
        return userDB.read(chatId);
    }

    @Override
    public User getUser(String phone) {
        if (!PHONE_TO_CHAT_ID.containsKey(phone)) throw UserNotFoundException.init(phone);
        Long chatId = PHONE_TO_CHAT_ID.get(phone);
        return getUser(chatId);
    }

    @Override
    public boolean hasUser(Long chatId) {
        return userDB.hasUser(chatId);
    }

    @Override
    public List<User> getAllUsers() {
        return userDB.getAllUsers();
    }

    private User getUserFromUserDTO(UserDTO userDTO) {
        return User.builder()
                .chatId(userDTO.chatId)
                .userName(userDTO.userName)
                .firstName(userDTO.firstName)
                .lastName(userDTO.lastName)
                .birthDate(userDTO.birthDate)
                .phone(userDTO.phone)
                .build();
    }
}
