package ru.pchelnikov.SpringBootDemo.App.TelegramHandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pchelnikov.SpringBootDemo.App.DTOs.MockServerUpdateDTO;
import ru.pchelnikov.SpringBootDemo.App.DTOs.MockServerUserDTO;
import ru.pchelnikov.SpringBootDemo.Domain.DTOs.UserDTO;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IMockServerServiceClient;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IUserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ru.pchelnikov.SpringBootDemo.App.TelegramHandlers.UserDTOHandler.createUserDTOFromUpdate;

@Slf4j
@Component
public class ReplyHandler {
    private final IUserService userService;
    private final IMockServerServiceClient mockServerServiceClient;
    private final Map<Long, ReplyMode> chatIdReplyMode = new HashMap<>();

    public ReplyHandler(IUserService userService, IMockServerServiceClient mockServerServiceClient) {
        this.userService = userService;
        this.mockServerServiceClient = mockServerServiceClient;
    }

    public String startReply(Update update) {
        String userName = update.getMessage().getFrom().getUserName();
        return "Hello, " + userName + "!\n" + this.helpReply();
    }

    public String helpReply() {
        return  "List of available commands:\n"
                + "/start or /hello - start bot,\n"
                + "/help - get help,\n"
                + "/info - get info that is available about you,\n"
                + "/birthday - if you want to set your birthday, \n"
                + "/phone - if you want to set your phone number.";
    }

    public String infoReply(Update update) {
        User user = userService.getUser(update.getMessage().getChatId());
        String reply = "Here is what I know about you:\n"
                + "userName: " + user.getUserName() + ",\n"
                + "firstName: " + user.getFirstName() + ",\n"
                + "lastName: " + user.getLastName() + ",\n"
                + "chatId: " + user.getChatId() + ",\n"
                + "birthDate: " + user.getBirthDate() + "\n"
                + "phone: " + user.getPhone();
        if (mockServerServiceClient.hasUser(user.getPhone())) {
            MockServerUserDTO userDTO = mockServerServiceClient.read(user.getPhone());
            reply += ", \n\n"
                    + "id on MockServer: " + userDTO.id + ", \n"
                    + "chatId on MockServer: " + userDTO.chatId + ", \n"
                    + "firstName on MockServer: " + userDTO.firstName + ", \n"
                    + "middleName on MockServer: " + userDTO.middleName + ", \n"
                    + "secondName on MockServer: " + userDTO.secondName + ", \n"
                    + "birthday on MockServer: " + userDTO.birthDay + ", \n"
                    + "isMale on MockServer: " + userDTO.male + ".";
        } else {
            reply += ".";
        }
        return reply;
    }

    public String birthdayReply() {
        return "Please, enter your birthday in following format: DD-MM-YYYY (you can change it later by sending /birthday command)";
    }

    public String phoneReply() {
        return "Please, enter your phone number (you can change it later by sending /phone command)";
    }

    public String prepareReply(Update update) {
        String reply;
        Long chatId = getChatId(update);
        if (!chatIdReplyMode.containsKey(chatId)) {
            chatIdReplyMode.put(chatId, ReplyMode.DEFAULT);
        }

        if (isPhoneNeeded(chatId)) {
            chatIdReplyMode.put(chatId, ReplyMode.EDIT_PHONE);
        }

        switch (chatIdReplyMode.get(chatId)) {
            case EDIT_BIRTHDAY:
                reply = replyToGetBirthdayFromUser(update);
                break;
            case EDIT_PHONE:
                reply = replyToGetPhoneFromUser(update);
                break;
            default:
                reply = replyToExecuteUserCommand(update);
        }
        return reply;
    }

    private boolean isPhoneNeeded(Long chatId) {
        String phone = userService.getUser(chatId).getPhone();
        return phone == null || !mockServerServiceClient.hasUser(phone);
    }

    private String replyToGetPhoneFromUser(Update update) {
        String phone = update.getMessage().getText().trim();
        String reply;
        UserDTO userDTO = createUserDTOFromUpdate(update);
        userDTO.phone = phone;
        userService.updateUser(userDTO);

        if (mockServerServiceClient.hasUser(phone)) {
            Long chatId = getChatId(update);
            chatIdReplyMode.put(chatId, ReplyMode.DEFAULT);
            updateUserOnMockServer(chatId);

            reply = "Phone number has been successfully entered, you are authorized";
            log.info("Phone number has been successfully retrieved for user {}", update.getMessage().getChatId());
        } else {
            reply = "User has not been found on MockServer. Please enter valid phone number " +
                    "or make sure that user with that phone actually exists on MockServer";
            log.info("User with phone number {} has not been found on MockServer", phone);
        }
        return reply;
    }

    private String replyToGetBirthdayFromUser(Update update) {
        String reply;
        try {
            parseBirthdateFromUserMessage(update);
            reply = "Date has been successfully entered";
            long chatId = getChatId(update);
            log.info("birthDate has been successfully retrieved for user {}", chatId);
            updateUserOnMockServer(chatId);
        } catch (ParseException e) {
            log.info("user {} entered invalid date", update.getMessage().getChatId());
            reply = "Couldn't recognize date, please, try again";
        }
        return reply;
    }

    private String replyToExecuteUserCommand(Update update) {
        String reply;
        String message = update.getMessage().getText();
        Long chatId = getChatId(update);

        switch(message.toLowerCase().trim()) {
            case ("/start"):
            case ("/hello"):
                reply = this.startReply(update);
                break;
            case ("/help"):
                reply = this.helpReply();
                break;
            case ("/info"):
                reply = this.infoReply(update);
                break;
            case("/birthday"):
                reply = this.birthdayReply();
                chatIdReplyMode.put(chatId, ReplyMode.EDIT_BIRTHDAY);
                break;
            case("/phone"):
                reply = this.phoneReply();
                chatIdReplyMode.put(chatId, ReplyMode.EDIT_PHONE);
                break;
            default:
                reply = message;
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
        chatIdReplyMode.put(chatId, ReplyMode.DEFAULT);
    }

    private Long getChatId(Update update) {
        return update.getMessage().getChatId();
    }

    private void updateUserOnMockServer(long chatId) {
        User telegramUser = userService.getUser(chatId);
        MockServerUserDTO mockServerUser = mockServerServiceClient.read(telegramUser.getPhone());
        Date birthDay =
                telegramUser.getBirthDate() != null ? telegramUser.getBirthDate() : mockServerUser.birthDay;
        MockServerUpdateDTO mockServerUpdateDTO = MockServerUpdateDTO.builder()
                .chatId(String.valueOf(telegramUser.getChatId()))
                .birthDay(birthDay)
                .phone(telegramUser.getPhone())
                .build();
        boolean success = mockServerServiceClient.update(mockServerUser.id, mockServerUpdateDTO);
        log.debug(String.valueOf(success));
    }
}
