package ru.pchelnikov.SpringBootDemo.TelegramHandlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pchelnikov.SpringBootDemo.Entities.User;
import ru.pchelnikov.SpringBootDemo.Services.UserService;

public class ReplyHandler {
    private static UserService userService = new UserService();

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
}
