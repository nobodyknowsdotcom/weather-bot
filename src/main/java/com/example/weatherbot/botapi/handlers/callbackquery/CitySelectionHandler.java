package com.example.weatherbot.botapi.handlers.callbackquery;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class CitySelectionHandler implements CallbackQueryHandler {
    private final UserService userService;

    public CitySelectionHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();

        String city = callbackQuery.getData().split("=")[1];
        userService.updateUserState(chatId, UserState.COMPLETE_REGISTRATION);
        userService.updateUserCity(chatId, city);

        SendMessage messageToSend = new SendMessage();
        String replyText = String.format("%s, твой город - %s", UserState.COMPLETE_REGISTRATION.getTitle(), city);
        messageToSend.setChatId(chatId);
        messageToSend.setText(replyText);

        return messageToSend;
    }

    @Override
    public UserState getHandlerName() {
        return UserState.REGISTRATION;
    }
}
