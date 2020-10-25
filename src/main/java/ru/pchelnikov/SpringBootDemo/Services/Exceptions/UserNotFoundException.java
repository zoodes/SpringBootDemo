package ru.pchelnikov.SpringBootDemo.Services.Exceptions;

public class UserNotFoundException extends UserServiceException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException init(Long chatId) {
        return new UserNotFoundException("User with chatID" + chatId + "has not been found!");
    }
}
