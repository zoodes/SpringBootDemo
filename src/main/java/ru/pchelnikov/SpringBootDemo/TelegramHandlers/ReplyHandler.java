package ru.pchelnikov.SpringBootDemo.TelegramHandlers;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Entities.User;
import ru.pchelnikov.SpringBootDemo.Services.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ru.pchelnikov.SpringBootDemo.TelegramHandlers.UserDTOHandler.createUserDTOFromUpdate;

@Slf4j
public class ReplyHandler {
    private static final UserService userService = new UserService();
    private static final Map<Long, Boolean> isChatIdInEditBirthdayMode = new HashMap<>();

    public static String startReply(Update update) {
        String userName = update.getMessage().getFrom().getUserName();
        return "Hello, " + userName + "!\n" + ReplyHandler.helpReply();
    }

    public static String helpReply() {
        return  "List of available commands:\n"
                + "/start or /hello - start bot,\n"
                + "/help - get help,\n"
                + "/info - get info that is available about you,\n"
                + "/birthday - if you want to set your birthday.";
    }

    public static String infoReply(Update update) {
        User user = userService.getUser(update.getMessage().getChatId());
        return "Here is what I know about you:\n"
                + "userName: " + user.getUserName() + ",\n"
                + "firstName: " + user.getFirstName() + ",\n"
                + "lastName: " + user.getLastName() + ",\n"
                + "chatId: " + user.getChatId() + ",\n"
                + "birthDate: " + user.getBirthDate() + ".";
    }

    public static String birthdayReply() {
        return "Please, enter your birthday in following format: DD-MM-YYYY";
    }

    public static String prepareReply(Update update) {
        String reply;
        Long chatId = getChatId(update);
        if (!isChatIdInEditBirthdayMode.get(chatId)) {
            reply = replyToExecuteUserCommand(update);
        } else {
            reply = replyToGetBirthdayFromUser(update);
        }
        return reply;
    }

    private static String replyToGetBirthdayFromUser(Update update) {
        String reply;
        try {
            parseBirthdateFromUserMessage(update);
            reply = "Date has been successfully entered";
            log.info("birthDate has been successfully retrieved");
        } catch (ParseException e) {
            log.info("user entered invalid date");
            reply = "Couldn't recognize date, please, try again";
        }
        return reply;
    }

    private static String replyToExecuteUserCommand(Update update) {
        String reply;
        String message = update.getMessage().getText();
        switch(message.toLowerCase().trim()) {
            case ("/start"):
            case ("/hello"):
                reply = ReplyHandler.startReply(update);
                break;
            case ("/help"):
                reply = ReplyHandler.helpReply();
                break;
            case ("/info"):
                reply = ReplyHandler.infoReply(update);
                break;
            case("/birthday"):
                reply = ReplyHandler.birthdayReply();
                Long chatId = getChatId(update);
                isChatIdInEditBirthdayMode.put(chatId, true);
                break;
            default:
                reply = message;
        }
        return reply;
    }

    private static void parseBirthdateFromUserMessage(Update update) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date birthDate = simpleDateFormat.parse(update.getMessage().getText().trim());
        UserDTO userDTO = createUserDTOFromUpdate(update);
        userDTO.birthDate = birthDate;
        userService.updateUser(userDTO);

        Long chatId = getChatId(update);
        isChatIdInEditBirthdayMode.put(chatId, false);
    }

    private static Long getChatId(Update update) {
        return update.getMessage().getChatId();
    }

    public static void setIsChatIdInEditBirthdayMode(Long chatId, boolean b) {
        isChatIdInEditBirthdayMode.put(chatId, b);
    }
}
