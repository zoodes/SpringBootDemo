package ru.pchelnikov.SpringBootDemo.app.dtos;

import lombok.Builder;

import java.util.Date;

@Builder
public class MockServerUpdateDTO {
    public Date birthDay;
    public String chatId;
    public String phone;
}
