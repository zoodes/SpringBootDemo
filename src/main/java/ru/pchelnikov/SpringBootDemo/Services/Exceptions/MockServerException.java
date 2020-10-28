package ru.pchelnikov.SpringBootDemo.Services.Exceptions;

public class MockServerException extends RuntimeException {
    public MockServerException(String message) {
        super(message);
    }
}
