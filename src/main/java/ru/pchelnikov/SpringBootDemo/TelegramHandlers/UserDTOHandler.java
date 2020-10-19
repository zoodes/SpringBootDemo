package ru.pchelnikov.SpringBootDemo.TelegramHandlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;

public class UserDTOHandler {
    public static UserDTO createUserDTOFromUpdate(Update update) {
        UserDTO userDTO = new UserDTO();
        userDTO.chatId = update.getMessage().getChatId();
        userDTO.userName = update.getMessage().getFrom().getUserName();
        userDTO.firstName = update.getMessage().getFrom().getFirstName();
        userDTO.lastName = update.getMessage().getFrom().getLastName();
        return userDTO;
    }
}
