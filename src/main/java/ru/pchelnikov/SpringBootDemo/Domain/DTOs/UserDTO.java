package ru.pchelnikov.SpringBootDemo.Domain.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    public long chatId;
    public String userName;
    public String firstName;
    public String lastName;
    public Date birthDate;
    public String phone;
}
