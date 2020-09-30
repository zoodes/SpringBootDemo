package ru.pchelnikov.SpringBootDemo;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
