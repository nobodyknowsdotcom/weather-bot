package com.example.weatherbot.controller;

import com.example.weatherbot.botapi.WeatherBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class BotController {
    private final WeatherBot weatherBot;

    public BotController(WeatherBot weatherBot) {
        this.weatherBot = weatherBot;
    }

    @PostMapping(value = "/")
    public BotApiMethod onUpdateReceived(@RequestBody Update update){
        return weatherBot.onWebhookUpdateReceived(update);
    }
}
