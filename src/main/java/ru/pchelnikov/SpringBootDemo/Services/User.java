package ru.pchelnikov.SpringBootDemo.Services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private long chatId;
    private String userName;
    private Date birthDate;
}
