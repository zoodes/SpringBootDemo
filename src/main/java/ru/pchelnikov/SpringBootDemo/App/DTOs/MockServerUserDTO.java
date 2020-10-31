package ru.pchelnikov.SpringBootDemo.App.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;
//TODO delete Lombok annotations

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockServerUserDTO {
    public Date birthDay;
    public Long chatId;
    public String firstName;
//    public String id;
    public UUID id;
    public Boolean male;
    public String middleName;
    public String phone;
    public String secondName;
}
