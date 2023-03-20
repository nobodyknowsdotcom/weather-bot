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

        userService.createUserIfNotExists(message.getChatId(), this.getHandlerType());

        return new SendMessage(message.getChatId().toString(), this.getHandlerType().getTitle());
    }

    @Override
    public UserState getHandlerType() {
        return UserState.START;
    }
}
