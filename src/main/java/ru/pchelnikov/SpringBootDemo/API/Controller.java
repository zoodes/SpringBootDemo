package ru.pchelnikov.SpringBootDemo.API;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Entities.User;
import ru.pchelnikov.SpringBootDemo.Services.Exceptions.UserNotFoundException;
import ru.pchelnikov.SpringBootDemo.Services.Exceptions.UserServiceException;
import ru.pchelnikov.SpringBootDemo.Services.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class Controller {

    private final IUserService service;

    @GetMapping("/users")
    HttpEntity<List<UserDTO>> getAllUsers() {
        List<User> users = service.getAllUsers();
        List<UserDTO> result = users.stream()
                .map(this::mapFromUserToUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{phone}")
    HttpEntity<UserDTO> getUser(@PathVariable String phone) {
        User user;
        try {
            user = service.getUser(phone);
        } catch (UserServiceException use) {
            throw UserNotFoundException.init(phone);
        }
        UserDTO result = mapFromUserToUserDTO(user);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/users/{phone}")
    HttpEntity<String> deleteUser(@PathVariable String phone) {
        try {
            service.deleteUser(phone);
        } catch (UserServiceException use) {
            throw UserNotFoundException.init(phone);
        }
        return ResponseEntity.ok("User with phone = " + phone + " was deleted");
    }

    private UserDTO mapFromUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.chatId = user.getChatId();
        userDTO.userName = user.getUserName();
        userDTO.firstName = user.getFirstName();
        userDTO.lastName = user.getLastName();
        userDTO.birthDate = user.getBirthDate();
        userDTO.phone = user.getPhone();
        return userDTO;
    }

}
