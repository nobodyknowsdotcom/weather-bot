package com.example.weatherbot.webhook;

import com.example.weatherbot.config.BotProperties;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Регистрирует адрес вебхука сразу после поднятия контекста
 */
@Component
@AllArgsConstructor
@Slf4j
public class WebhookSetup {
    private final BotProperties botProperties;
    @SneakyThrows
    @PostConstruct
    public void registerWebhook(){

        URL requestUrl = new URL(String.format("https://api.telegram.org/bot%s/setWebhook?url=%s",
                botProperties.getToken(),
                botProperties.getWebhookPath()
        ));

        try {
            HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
            con.setRequestMethod("GET");
            log.info(String.format("Register webhook successfully, response is %s", con.getResponseMessage()));
        } catch (IOException e) {
            log.error(String.format("Cannot register telegram webhook by path %s", requestUrl.getPath()));
        }
    }
}