package ru.pchelnikov.SpringBootDemo.App.Web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pchelnikov.SpringBootDemo.Domain.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;
import ru.pchelnikov.SpringBootDemo.Domain.Exceptions.UserNotFoundException;
import ru.pchelnikov.SpringBootDemo.Domain.Exceptions.UserServiceException;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@Api("Telegram bot controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class TelegramController {

    private final IUserService service;

    @ApiOperation("return an array of all users")
    @GetMapping
    HttpEntity<List<UserDTO>> getAllUsers() {
        List<User> users = service.getAllUsers();
        List<UserDTO> result = users.stream()
                .map(this::mapFromUserToUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @ApiOperation("return user with specific phone number")
    @GetMapping("/{phone}")
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

    @ApiOperation("delete user with specific phone number")
    @DeleteMapping("/{phone}")
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
