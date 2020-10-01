package ru.pchelnikov.SpringBootDemo.Services;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserService {
    private static final Map<Long, User> USER_MAP = new HashMap<>();

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

        if (!USER_MAP.containsKey(user.getChatId())) {
            USER_MAP.put(user.getChatId(), user);
            log.info("User {} has been added to userList!", user.getUserName());
        } else {
            log.warn("userList already contains user {}!", user.getUserName());
        }
        log.info("userList now consists of: " + USER_MAP.values().toString());
    }
}
