package ru.pchelnikov.SpringBootDemo.App.Exceptions;

public class MockServerException extends RuntimeException {
    public MockServerException(String message) {
        super(message);
    }
}
