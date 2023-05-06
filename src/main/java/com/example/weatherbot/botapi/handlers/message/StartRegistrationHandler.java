package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.botapi.factory.InlineKeyboardFactory;
import com.example.weatherbot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class StartRegistrationHandler implements MessageHandler{
    private final InlineKeyboardFactory inlineKeyboardFactory;
    private final UserService userService;

    public StartRegistrationHandler(InlineKeyboardFactory inlineKeyboardFactory, UserService userService) {
        this.inlineKeyboardFactory = inlineKeyboardFactory;
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage messageToSend = new SendMessage(message.getChatId().toString(), this.getInputType().getTitle());
        messageToSend.setReplyMarkup(inlineKeyboardFactory.getPopularCitiesInlineKeyboard());

        userService.updateUserState(message.getChatId(), getOutputType());

        return messageToSend;
    }

    @Override
    public UserState getInputType() {
        return UserState.START_REGISTRATION;
    }

    @Override
    public UserState getOutputType() {
        return UserState.IN_REGISTRATION;
    }
}
