package ru.pchelnikov.SpringBootDemo.Services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.pchelnikov.SpringBootDemo.Services.ITelegramService;
import ru.pchelnikov.SpringBootDemo.TelegramHandlers.BotHandler;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class TelegramService implements ITelegramService {


    BotHandler botHandler;

    public TelegramService(BotHandler botHandler) {
        this.botHandler = botHandler;
    }

    @Override
    @PostConstruct
    public void setup() {
//        log.info("Initializing TelegramBot");
//        ApiContextInitializer.init();
        log.info("Launching TelegramBot");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(botHandler);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}