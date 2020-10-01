package ru.pchelnikov.SpringBootDemo.Handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.pchelnikov.SpringBootDemo.Services.UserService;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class BotHandler extends TelegramLongPollingBot {
//    @Value("${bot.token}")
//    private static String TOKEN;
    private static final String TOKEN = "1273448729:AAEsX77rwpBf-i1iYFLkbvOFctnUEVsY6vc";

//    @Value("${bot.name}")
//    private static String BOT_NAME;
    private static final String BOT_NAME = "pchel_test_bot";
    private static boolean isWaitingForRightAnswer = false;

    /**
     * method for receiving messages
     * @param update contains message from user
     */
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        log.info("New message received: {}", update.getMessage().toString());

        if (!UserService.hasUser(update.getMessage().getChatId())) {
            UserService.createUser(update);
        }

        String reply;

        if (!isWaitingForRightAnswer) {
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
                    isWaitingForRightAnswer = true;
                    break;
                default:
                    reply = message;
            }
        } else {
            log.info("Start retrieving birthDate from user...");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date birthDate = simpleDateFormat.parse(update.getMessage().getText());
                UserService.updateBirthDate(update, birthDate);
                isWaitingForRightAnswer = false;
                reply = "Date has been successfully entered";
                log.info("birthDate has been successfully retrieved");
            } catch (ParseException e) {
                log.info("user entered invalid date");
                reply = "Couldn't recognize date, please, try again";
            }
        }

        sendMsg(update.getMessage().getChatId().toString(), reply);
        log.info("The reply was sent back to user");


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

    @PostConstruct
    public static void startBot() {
        log.info("Launching TelegramBot");
        log.info("Bot name used: {}", BOT_NAME);
        log.info("Token used: {}", TOKEN);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new BotHandler());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}

