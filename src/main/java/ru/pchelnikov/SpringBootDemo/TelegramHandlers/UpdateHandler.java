package ru.pchelnikov.SpringBootDemo.TelegramHandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Services.IUserService;
import java.util.ArrayList;

import static ru.pchelnikov.SpringBootDemo.TelegramHandlers.UserDTOHandler.createUserDTOFromUpdate;

@Slf4j
@Component
public class UpdateHandler extends TelegramLongPollingBot {
    @Value("${bot.token}")
    private String TOKEN;
    @Value("${bot.name}")
    private String BOT_NAME;
    private final IUserService userService;
    private final ReplyHandler replyHandler;
    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public UpdateHandler(IUserService userService, ReplyHandler replyHandler) {
        this.userService = userService;
        this.replyHandler = replyHandler;
    }

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        log.info("New message from user {} received: \"{}\"", chatId, update.getMessage().getText());
        createUserIfNotExists(update);
        String reply = replyHandler.prepareReply(update);
        sendReply(update, reply);
        log.info("The reply was sent back to user {}: \"{}\"", chatId, reply.replaceAll("[\\n\\r]", " "));
    }

    private void createUserIfNotExists(Update update) {
        Long chatId = update.getMessage().getChatId();
        if (!userService.hasUser(chatId)) {
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
        keyboardFirstRow.add("/phone");
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