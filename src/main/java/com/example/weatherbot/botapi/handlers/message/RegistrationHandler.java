package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class RegistrationHandler implements MessageHandler{
    private final UserService userService;

    public RegistrationHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {

        userService.updateUserState(message.getChatId(), this.getHandlerType());

        return new SendMessage(message.getChatId().toString(), this.getHandlerType().getTitle());
    }

    @Override
    public UserState getHandlerType() {
        return UserState.REGISTRATION;
    }
}
