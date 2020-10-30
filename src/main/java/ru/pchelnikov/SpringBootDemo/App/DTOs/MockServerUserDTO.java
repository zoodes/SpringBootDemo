package ru.pchelnikov.SpringBootDemo.App.DTOs;

import lombok.ToString;

import java.util.Date;

@ToString
public class MockServerUserDTO {
    public Date birthDay;
    public Long chatId;
    public String firstName;
    public String id;
    public Boolean male;
    public String middleName;
    public String phone;
    public String secondName;
}
