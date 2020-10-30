package ru.pchelnikov.SpringBootDemo.Domain.Exceptions;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message) {
        super(message);
    }
}
