package com.example.weatherbot.botapi.handlers;

import com.example.weatherbot.botapi.handlers.message.MessageHandler;
import com.example.weatherbot.enums.UserState;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BotStateContext {
    private final Map<UserState, MessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<MessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getInputType(), handler));
    }
    public SendMessage processInputMessage(Message message, UserState currentState) {

        MessageHandler currentMessageHandler = findMessageHandler(currentState);

        if (currentMessageHandler == null) { // Если хендлера еще не существует, бот просто молчит.
            return new SendMessage();
        }

        return currentMessageHandler.handleMessage(message);
    }

    private MessageHandler findMessageHandler(UserState currentState) {
        return messageHandlers.get(currentState);
    }
}
