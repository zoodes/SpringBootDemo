package ru.pchelnikov.SpringBootDemo.App.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
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
public class UserDto {
    @JsonProperty
    public Date birthDay;
    @JsonProperty
    public String chatId;
    @JsonProperty
    public String firstName;
//    public String id;
    @JsonSerialize(using = UUIDSerializer.class)
    @JsonDeserialize(using = UUIDDeserializer.class)
    public UUID id;
    @JsonProperty
    public Boolean male;
    @JsonProperty
    public String middleName;
    @JsonProperty
    public String phone;
    @JsonProperty
    public String secondName;
}
