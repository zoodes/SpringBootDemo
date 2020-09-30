package ru.pchelnikov.SpringBootDemo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int chatId;
    private String userName;
    private Date birthDate;
}
