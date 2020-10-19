package ru.pchelnikov.SpringBootDemo.TelegramHandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Services.IUserService;
import javax.annotation.PostConstruct;
import java.util.ArrayList;

import static ru.pchelnikov.SpringBootDemo.TelegramHandlers.UserDTOHandler.createUserDTOFromUpdate;

@Slf4j
@Component
public class BotHandler extends TelegramLongPollingBot {
    @Value("${bot.token}")
    private String TOKEN;
    @Value("${bot.name}")
    private String BOT_NAME;
    @Autowired
    private IUserService userService;
    @Autowired
    private LongPollingBot botHandler;

    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return TOKEN;
    }

    @PostConstruct
    public void startBot() {
        log.info("Launching TelegramBot");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(botHandler);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("New message received: {}", update.getMessage().toString());
        createUserIfNotExists(update);
        String reply = ReplyHandler.prepareReply(update);
        sendReply(update, reply);
        log.info("The reply was sent back to user");
    }

    private void createUserIfNotExists(Update update) {
        Long chatId = update.getMessage().getChatId();
        if (!userService.hasUser(chatId)) {
            ReplyHandler.setIsChatIdInEditBirthdayMode(chatId, false);
            UserDTO userDTO = createUserDTOFromUpdate(update);
            userService.createUser(userDTO);
        }
    }

    public synchronized void sendReply(Update update, String replyText) {
        setupKeyboard();
        SendMessage sendMessage = getAndSetupSendMessage(update, replyText);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("TelegramApiException has erupted: ", e);
        }
    }

    public SendMessage getAndSetupSendMessage(Update update, String replyText) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(replyText);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    private void setupKeyboard() {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        keyboardFirstRow.add("/birthday");
        keyboardSecondRow.add("/help");
        keyboardSecondRow.add("/info");

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}