package ru.pchelnikov.SpringBootDemo.Services;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.ArrayList;

@Slf4j
public class UserService {
    private static final ArrayList<User> USER_LIST = new ArrayList<>();

    /**
     * creates new User and adds him to USER_LIST if
     * it doesn't already contain him
     * @param update update from telegram
     */
    public static void createUser(Update update) {
        User user = new User(
                update.getMessage().getChatId(),
                update.getMessage().getFrom().getUserName(),
                null
        );
        log.info("User {} has been created", user.getUserName());

        if (!USER_LIST.contains(user)) {
            USER_LIST.add(user);
            log.info("User {} has been added to userList!", user.getUserName());
        } else {
            log.warn("userList already contains user {}!", user.getUserName());
        }
        log.info("userList now consists of: " + USER_LIST.toString());
    }
}
