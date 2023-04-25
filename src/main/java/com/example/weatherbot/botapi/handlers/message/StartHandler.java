package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartHandler implements MessageHandler{
    private final UserService userService;

    public StartHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {

        userService.createUserIfNotExists(message.getChatId(), this.getInputType());

        return new SendMessage(message.getChatId().toString(), this.getInputType().getTitle());
    }

    @Override
    public UserState getInputType() {
        return UserState.START;
    }

    @Override
    public UserState getOutputType() {
        return UserState.START;
    }
}
