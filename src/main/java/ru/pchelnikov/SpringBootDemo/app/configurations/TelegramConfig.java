package ru.pchelnikov.SpringBootDemo.app.configurations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.pchelnikov.SpringBootDemo.app.telegram.UpdateHandler;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TelegramConfig {

    private final UpdateHandler updateHandler;

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        log.info("Launching TelegramBot");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(updateHandler);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
        return telegramBotsApi;
    }

}