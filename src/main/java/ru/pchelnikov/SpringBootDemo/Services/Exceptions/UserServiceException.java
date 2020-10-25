package ru.pchelnikov.SpringBootDemo.Services.Exceptions;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }
}
