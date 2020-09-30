package ru.pchelnikov.SpringBootDemo.Handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.pchelnikov.SpringBootDemo.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

//1273448729:AAEsX77rwpBf-i1iYFLkbvOFctnUEVsY6vc
//PchelBot
//pchel_study_bot

@Slf4j
public class BotHandler extends TelegramLongPollingBot {
    private static final String TOKEN = "1273448729:AAEsX77rwpBf-i1iYFLkbvOFctnUEVsY6vc";
    private static final String BOT_NAME = "pchel_study_bot";
    private static ArrayList<User> userList = new ArrayList<>();

    /**
     * method for receiving messages
     * @param update contains message from user
     */
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        log.info("New message received: {}", update.getMessage().toString());

        String reply;
        switch(message.toLowerCase()) {
            case ("/start"):
            case ("/hello"):
                String userName = update.getMessage().getFrom().getFirstName()
                        + " " + update.getMessage().getFrom().getLastName();
                reply = "Hello, " + userName;
                break;
            case ("/info"):
                reply = "Here is what I know about you: " +
                        update.getMessage().getFrom().toString();
                break;
            default:
                reply = message;
        }

        sendMsg(update.getMessage().getChatId().toString(), reply);
        log.info("The same message was sent back to user");

        log.info("Trying to create new User, if he/she doesn't exist");
        createUser(update);
    }

    private void createUser(Update update) {
        User user = new User(
                update.getMessage().getChatId(),
                update.getMessage().getFrom().getFirstName()
                        + " " + update.getMessage().getFrom().getLastName(),
                null
                );
        log.info("User {} has been created", user.getUserName());

        if (!userList.contains(user)) {
            userList.add(user);
            log.info("User {} has been added to userList!", user.getUserName());
        } else {
            log.warn("userList already contains user {}!", user.getUserName());
        }
        log.info("userList now consists of: " + userList.toString());
    }

    /**
     * Method for configuting and sending of message.
     * @param chatId id of chat
     * @param s string, which will be sent as a message
     */
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("TelegramApiException has erupted: ", e);
        }
    }

    /**
     * Method returns bot name, entered during registration
     * @return bot name
     */
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Method returns bot token from TG servers
     * @return bot token
     */
    public String getBotToken() {
        return TOKEN;
    }


    public static void startBot() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new BotHandler());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}

