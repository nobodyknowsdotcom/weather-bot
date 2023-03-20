package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.factory.InlineKeyboardFactory;
import com.example.weatherbot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class RegistrationHandler implements MessageHandler{
    private final InlineKeyboardFactory inlineKeyboardFactory;
    private final UserService userService;

    public RegistrationHandler(InlineKeyboardFactory inlineKeyboardFactory, UserService userService) {
        this.inlineKeyboardFactory = inlineKeyboardFactory;
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage messageToSend = new SendMessage(message.getChatId().toString(), this.getHandlerType().getTitle());
        messageToSend.setReplyMarkup(inlineKeyboardFactory.getPopularCitiesInlineKeyboard());

        userService.updateUserState(message.getChatId(), this.getHandlerType());

        return messageToSend;
    }

    @Override
    public UserState getHandlerType() {
        return UserState.REGISTRATION;
    }
}
