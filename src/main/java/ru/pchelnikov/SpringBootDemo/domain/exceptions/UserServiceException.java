package ru.pchelnikov.SpringBootDemo.domain.exceptions;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }
}
