package com.example.weatherbot.botapi;

import com.example.weatherbot.botapi.handlers.CallbackQueryContext;
import com.example.weatherbot.botapi.handlers.BotStateContext;
import com.example.weatherbot.model.User;
import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@Slf4j
public class BotFacade {
    private final CallbackQueryContext callbackQueryContext;
    private final BotStateContext userStateContext;
    private final UserService userService;

    public BotFacade(CallbackQueryContext callbackQueryContext, BotStateContext userStateContext, UserService userService) {
        this.callbackQueryContext = callbackQueryContext;
        this.userStateContext = userStateContext;
        this.userService = userService;
    }

    public SendMessage handleUpdate(Update update){
        SendMessage replyMessage = new SendMessage();

        if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            replyMessage = callbackQueryContext.handleCallbackQuery(callbackQuery);
        }

        if(update.hasMessage() && update.getMessage().hasLocation()){
            Message message = update.getMessage();
            replyMessage = handleLocation(message);
        }

        if(update.hasMessage() && update.getMessage().hasText()){
            Message message = update.getMessage();
            replyMessage = handleMessage(message);
        }

        replyMessage.setParseMode("Markdown");
        return replyMessage;
    }

    private SendMessage handleMessage(Message message){
        String inputMessage = message.getText();
        UserState userState;

        switch (inputMessage) {
            case "/start" -> userState = UserState.START;
            case "/registration" -> userState = UserState.START_REGISTRATION;
            case "/forcity" -> userState = UserState.INIT_FORECAST_FOR_CITY;
            case "/schedule" -> userState = UserState.CHANGE_SCHEDULE_SETTINGS;
            default -> {
                Optional<User> optionalUser = userService.findUserByChatId(message.getChatId());

                if (optionalUser.isPresent()) {
                    // if user is in database, get his state
                    User user = optionalUser.get();
                    log.info("Processing user {} with state {}", user.getChatId(), user.getUserState());
                    userState = user.getUserState();
                } else {
                    // if user never used bot, set his state to START and call StartHandler
                    log.info("Processing new user {} with state {}", message.getChatId(), UserState.START);
                    userState = UserState.START;
                }
            }
        }

        return userStateContext.processInputMessage(message, userState);
    }

    private SendMessage handleLocation(Message message){
        UserState userState;
        Optional<User> optionalUser = userService.findUserByChatId(message.getChatId());

        if (optionalUser.isEmpty()){
            userState = UserState.START;
        } else {
            userState = optionalUser.get().getUserState();
        }

        return userStateContext.processInputMessage(message, userState);
    }
}
