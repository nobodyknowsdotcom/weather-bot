package com.example.weatherbot.botapi;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class WeatherBot extends TelegramWebhookBot {
    private final BotFacade botFacade;

    public WeatherBot(BotFacade botFacade) {
        this.botFacade = botFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return botFacade.handleUpdate(update);
    }

    @Override
    public void setWebhook(SetWebhook setWebhook) throws TelegramApiException {
    }

    @Override
    public String getBotPath() {
        return null;
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }
}
