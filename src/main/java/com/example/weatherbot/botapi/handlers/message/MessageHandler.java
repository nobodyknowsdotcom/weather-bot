package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {
    SendMessage handleMessage(Message message);
    UserState getInputType();
    UserState getOutputType();
}
