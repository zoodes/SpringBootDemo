package ru.pchelnikov.SpringBootDemo.App.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@ToString
public class MockServerUserDTO {
    public Date birthDay;
    public String chatId;
    public String firstName;
    @JsonSerialize(using = UUIDSerializer.class)
    @JsonDeserialize(using = UUIDDeserializer.class)
    public UUID id;
    public Boolean male;
    public String middleName;
    public String phone;
    public String secondName;
}
