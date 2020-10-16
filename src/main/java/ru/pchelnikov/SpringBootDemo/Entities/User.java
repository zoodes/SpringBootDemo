package ru.pchelnikov.SpringBootDemo.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private long chatId;
    private String userName;
    private String firstName;
    private String lastName;
    private Date birthDate;
}
