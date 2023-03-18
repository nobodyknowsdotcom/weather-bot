package com.example.weatherbot.botapi.handlers.callbackquery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryContext {
    public SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        // TODO
        return new SendMessage(callbackQuery.getId(), callbackQuery.getData());
    }
}
