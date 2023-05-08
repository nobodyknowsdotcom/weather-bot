package com.example.weatherbot.botapi.handlers;

import com.example.weatherbot.botapi.handlers.callbackquery.CallbackQueryHandler;
import com.example.weatherbot.enums.UserState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CallbackQueryContext {
    private final Map<UserState, CallbackQueryHandler> callbackQueryHandlers = new HashMap<>();
    public CallbackQueryContext(List<CallbackQueryHandler> callbackQueryHandlers) {
        callbackQueryHandlers.forEach(handler -> this.callbackQueryHandlers.put(handler.getHandlerName(), handler));
    }
    public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        SendMessage messageToSend = new SendMessage();

        switch (callbackQuery.getData().split("=")[0]){
            case "city" -> messageToSend = callbackQueryHandlers.get(UserState.IN_REGISTRATION).handle(callbackQuery);
        }

        return messageToSend;
    }
}
