package ru.pchelnikov.SpringBootDemo.Services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.pchelnikov.SpringBootDemo.Services.ITelegramService;
import ru.pchelnikov.SpringBootDemo.TelegramHandlers.UpdateHandler;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class TelegramService implements ITelegramService {


    UpdateHandler updateHandler;

    public TelegramService(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    @Override
    @PostConstruct
    public void setup() {
//        log.info("Initializing TelegramBot");
//        ApiContextInitializer.init();
        log.info("Launching TelegramBot");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(updateHandler);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}