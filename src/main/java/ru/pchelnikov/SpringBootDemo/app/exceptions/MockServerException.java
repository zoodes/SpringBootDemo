package ru.pchelnikov.SpringBootDemo.app.exceptions;

public class MockServerException extends RuntimeException {
    public MockServerException(String message) {
        super(message);
    }
}
