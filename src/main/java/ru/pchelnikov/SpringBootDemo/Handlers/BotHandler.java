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

import javax.annotation.PostConstruct;

//1273448729:AAEsX77rwpBf-i1iYFLkbvOFctnUEVsY6vc
//PchelBot
//pchel_study_bot

@Slf4j
@Component
public class BotHandler extends TelegramLongPollingBot {
    private static final String TOKEN = "1273448729:AAEsX77rwpBf-i1iYFLkbvOFctnUEVsY6vc";
    private static final String BOT_NAME = "pchel_study_bot";

    /**
     * method for receiving messages
     * @param update contains message from user
     */
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
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
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new BotHandler());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}

