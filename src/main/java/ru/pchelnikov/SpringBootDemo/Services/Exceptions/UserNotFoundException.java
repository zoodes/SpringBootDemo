package ru.pchelnikov.SpringBootDemo.Services.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not Found")
public class UserNotFoundException extends UserServiceException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException init(Long chatId) {
        return new UserNotFoundException("User with chatID" + chatId + "has not been found!");
    }
}
