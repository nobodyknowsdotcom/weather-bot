package com.example.weatherbot.botapi;

import com.example.weatherbot.config.BotProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class WeatherBot extends TelegramWebhookBot {
    private final BotProperties botProperties;
    private final BotFacade botFacade;

    public WeatherBot(BotProperties botProperties, @Lazy BotFacade botFacade) {
        this.botProperties = botProperties;
        this.botFacade = botFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return botFacade.handleUpdate(update);
    }

    @Override
    public void setWebhook(SetWebhook setWebhook){}

    @Override
    public String getBotPath() {
        return null;
    }

    @SneakyThrows
    public void sendMessage(Long chatId, String messageText){

        SendMessage message = new SendMessage(chatId.toString(), messageText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("cannot execute message {} to {}", message, chatId);
        }
    }

    public void sendEditReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup){
        try {
            execute(editMessageReplyMarkup);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("cannot edit markup {}", editMessageReplyMarkup.toString());
        }
    }

    public void sendEditMessageText(EditMessageText editMessageText){
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("cannot edit message {}", editMessageText.toString());
        }
    }

    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }
}
