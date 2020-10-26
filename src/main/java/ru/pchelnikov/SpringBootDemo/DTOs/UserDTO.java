package ru.pchelnikov.SpringBootDemo.DTOs;

import lombok.ToString;

import java.util.Date;
@ToString
public class UserDTO {
    public long chatId;
    public String userName;
    public String firstName;
    public String lastName;
    public Date birthDate;
    public String phone;
}
