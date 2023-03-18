package com.example.weatherbot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BotProperties {
    @Value("${bot.webhook-path}")
    private String webhookPath;
    @Value("${bot.name}")
    private String name;
    @Value("${bot.token}")
    private String token;
}
