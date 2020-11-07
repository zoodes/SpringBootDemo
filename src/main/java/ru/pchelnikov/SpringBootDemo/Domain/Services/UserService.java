package ru.pchelnikov.SpringBootDemo.Domain.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.pchelnikov.SpringBootDemo.Domain.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Domain.Exceptions.UserNotFoundException;
import ru.pchelnikov.SpringBootDemo.Domain.Repositories.UserCrudRepository;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IUserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Primary
public class UserService implements IUserService {
    private final UserCrudRepository userCrudRepository;

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

            log.info("User {} has been updated!", newUser.getChatId());
            log.debug("Userlist now contains: {}", getAllUsers());
        }
    }

    @Override
    public void deleteUser(Long chatId) {
        Optional<User> userOptional = userCrudRepository.findById(chatId);
        if (!userOptional.isPresent()) {
            throw UserNotFoundException.init(chatId);
        } else {
            User user = userOptional.get();
            log.info("User {} is about to be deleted!", user.getChatId());
            userCrudRepository.delete(user);
            log.info("Deletion complete!");
        }
    }

    @Override
    public void deleteUser(String phone) {
        User user = getUser(phone);
        deleteUser(user.getChatId());
    }

    @Override
    public User getUser(Long chatId) {
        Optional<User> userOptional = userCrudRepository.findById(chatId);
        if (!userOptional.isPresent()) {
            throw UserNotFoundException.init(chatId);
        } else {
            return userOptional.get();
        }
    }

    @Override
    public User getUser(String phone) {
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
