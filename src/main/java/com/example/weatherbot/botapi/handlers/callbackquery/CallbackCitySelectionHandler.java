package com.example.weatherbot.botapi.handlers.callbackquery;

import com.example.weatherbot.botapi.WeatherBot;
import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.botapi.factory.InlineKeyboardFactory;
import com.example.weatherbot.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class CallbackCitySelectionHandler implements CallbackQueryHandler {
    private final InlineKeyboardFactory inlineKeyboardFactory;
    private final UserService userService;
    private final WeatherBot weatherBot;

    public CallbackCitySelectionHandler(InlineKeyboardFactory inlineKeyboardFactory, UserService userService, WeatherBot weatherBot) {
        this.inlineKeyboardFactory = inlineKeyboardFactory;
        this.userService = userService;
        this.weatherBot = weatherBot;
    }

    @Override
    @Transactional
    public SendMessage handle(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();

        // start a transaction, change user state and city
        String city = callbackQuery.getData().split("=")[1];
        userService.updateUserState(chatId, this.getOutputType());
        userService.updateUserCity(chatId, city);

        // add green checkbox to city chosen by user
        InlineKeyboardMarkup checkboxMarkup = inlineKeyboardFactory.getPopularCitiesWithChosenCity(city);
        EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                .replyMarkup(checkboxMarkup)
                .messageId(callbackQuery.getMessage().getMessageId())
                .inlineMessageId(callbackQuery.getInlineMessageId())
                .chatId(chatId).build();
        weatherBot.sendEditReplyMarkup(editMessageReplyMarkup);

        return new SendMessage();
    }

    @Override
    public UserState getInputType() {
        return UserState.IN_REGISTRATION;
    }

    @Override
    public UserState getOutputType() {
        return UserState.COMPLETE_REGISTRATION;
    }
}
