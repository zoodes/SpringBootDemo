package ru.pchelnikov.SpringBootDemo.Domain.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pchelnikov.SpringBootDemo.Domain.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Domain.Repositories.UserCrudRepository;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IUserDAO;
import ru.pchelnikov.SpringBootDemo.Domain.Exceptions.UserNotFoundException;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {
    private final UserCrudRepository userCrudRepository;
//    private final Map<String, Long> PHONE_TO_CHAT_ID = new HashMap<>();

    public UserService(UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
    }

    @Override
    public void createUser(UserDTO userDTO) {
        User user = getUserFromUserDTO(userDTO);
        if (!userCrudRepository.existsById(user.getChatId())) {
            userCrudRepository.save(user);
            log.info("User {} has been added to userList!", user.getChatId());
        } else {
            log.warn("userList already contains user {}!", user.getChatId());
        }
        log.debug("Userlist now contains: {}", getAllUsers());
    }


    @Override
    public void updateUser(UserDTO userDTO) {
        //todo: change this into Optional.isPresent();
//        if (!userCrudRepository.existsById(userDTO.chatId)) throw UserNotFoundException.init(userDTO.chatId);
//        User oldUser = userCrudRepository.findById(userDTO.chatId).get();
        Optional<User> userOptional = userCrudRepository.findById(userDTO.chatId);
        if (!userOptional.isPresent()) {
            throw UserNotFoundException.init(userDTO.chatId);
        } else {
            User oldUser = userOptional.get();
            User newUser = User.builder()
                    .chatId(userDTO.chatId)
                    .userName(userDTO.userName != null ? userDTO.userName : oldUser.getUserName())
                    .firstName(userDTO.firstName != null ? userDTO.firstName : oldUser.getFirstName())
                    .lastName(userDTO.lastName != null ? userDTO.lastName : oldUser.getLastName())
                    .birthDate(userDTO.birthDate != null ? userDTO.birthDate : oldUser.getBirthDate())
                    .phone(userDTO.phone != null ? userDTO.phone : oldUser.getPhone())
                    .build();
            userCrudRepository.save(newUser);
            //TODO remove this
//        if (newUser.getPhone() != null) {
//            PHONE_TO_CHAT_ID.put(newUser.getPhone(), newUser.getChatId());
//        }

            log.info("User {} has been updated!", newUser.getChatId());
            log.debug("Userlist now contains: {}", getAllUsers());
        }
    }

    @Override
    public void deleteUser(Long chatId) {
        //todo: change this into Optional.isPresent();
//        if (!userCrudRepository.existsById(chatId)) throw UserNotFoundException.init(chatId);
//        User user = userCrudRepository.findById(chatId).get();
        Optional<User> userOptional = userCrudRepository.findById(chatId);
        if (!userOptional.isPresent()) {
            throw UserNotFoundException.init(chatId);
        } else {
            User user = userOptional.get();
            log.info("User {} is about to be deleted!", user.getChatId());
            userCrudRepository.delete(user);
            //todo remove  this
//        PHONE_TO_CHAT_ID.remove(user.getPhone());
            log.info("Deletion complete!");
        }
    }

    @Override
    public void deleteUser(String phone) {
        //todo rewrite whole method
//        if (!PHONE_TO_CHAT_ID.containsKey(phone)) throw UserNotFoundException.init(phone);
//        Long chatId = PHONE_TO_CHAT_ID.get(phone);
//        deleteUser(chatId);
        User user = getUser(phone);
        deleteUser(user.getChatId());
    }

    @Override
    public User getUser(Long chatId) {
        //TODO rewrite method using Optional
//        if (!userCrudRepository.existsById(chatId)) throw UserNotFoundException.init(chatId);
//        return userCrudRepository.findById(chatId).get();
        Optional<User> userOptional = userCrudRepository.findById(chatId);
        if (!userOptional.isPresent()) {
            throw UserNotFoundException.init(chatId);
        } else {
            return userOptional.get();
        }
    }

    @Override
    public User getUser(String phone) {
        //todo rewrite whole method
//        if (!PHONE_TO_CHAT_ID.containsKey(phone)) throw UserNotFoundException.init(phone);
//        Long chatId = PHONE_TO_CHAT_ID.get(phone);
//        return getUser(chatId);
        Optional<User> user = userCrudRepository.findDistinctFirstByPhone(phone);
        if (!user.isPresent()) {
            throw UserNotFoundException.init(phone);
        } else {
            return user.get();
        }
    }

    @Override
    public boolean hasUser(Long chatId) {
        return userCrudRepository.existsById(chatId);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userCrudRepository.findAll();
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
