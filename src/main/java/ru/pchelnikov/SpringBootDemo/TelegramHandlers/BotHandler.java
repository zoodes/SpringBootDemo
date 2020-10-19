package ru.pchelnikov.SpringBootDemo.TelegramHandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.pchelnikov.SpringBootDemo.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Services.IUserService;
import ru.pchelnikov.SpringBootDemo.Services.UserService;
import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class BotHandler extends TelegramLongPollingBot {
    @Value("${bot.token}")
    private String TOKEN;
    @Value("${bot.name}")
    private String BOT_NAME;
    @Autowired
    private ApplicationContext context;

    private final Map<Long, Boolean> isChatIdInEditBirthdayMode = new HashMap<>();
    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private final IUserService userService = new UserService();

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
            telegramBotsApi.registerBot(context.getBean(BotHandler.class));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("New message received: {}", update.getMessage().toString());
        createUserIfNotExists(update);
        String reply = prepareReply(update);
        sendReply(update, reply);
        log.info("The reply was sent back to user");
    }

    private String prepareReply(Update update) {
        String reply;
        Long chatId = getChatId(update);
        if (!isChatIdInEditBirthdayMode.get(chatId)) {
            reply = replyToExecuteUserCommand(update);
        } else {
            reply = replyToGetBirthdayFromUser(update);
        }
        return reply;
    }

    private String replyToGetBirthdayFromUser(Update update) {
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

    private void parseBirthdateFromUserMessage(Update update) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date birthDate = simpleDateFormat.parse(update.getMessage().getText().trim());
        UserDTO userDTO = createUserDTOFromUpdate(update);
        userDTO.birthDate = birthDate;
        userService.updateUser(userDTO);

        Long chatId = getChatId(update);
        isChatIdInEditBirthdayMode.put(chatId, false);
    }

    private String replyToExecuteUserCommand(Update update) {
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

    private void createUserIfNotExists(Update update) {
        Long chatId = getChatId(update);
        if (!userService.hasUser(chatId)) {
            isChatIdInEditBirthdayMode.put(chatId, false);
            UserDTO userDTO = createUserDTOFromUpdate(update);
            userService.createUser(userDTO);
        }
    }

    private static UserDTO createUserDTOFromUpdate(Update update) {
        UserDTO userDTO = new UserDTO();
        userDTO.chatId = getChatId(update);
        userDTO.userName = update.getMessage().getFrom().getUserName();
        userDTO.firstName = update.getMessage().getFrom().getFirstName();
        userDTO.lastName = update.getMessage().getFrom().getLastName();
        return userDTO;
    }

    public SendMessage getAndSetupSendMessage(Update update, String replyText) {
        Long chatId = getChatId(update);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(replyText);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
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


    private static Long getChatId(Update update) {
        return update.getMessage().getChatId();
    }
}