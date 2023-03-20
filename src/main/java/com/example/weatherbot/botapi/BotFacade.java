package com.example.weatherbot.botapi;

import com.example.weatherbot.botapi.handlers.callbackquery.CallbackQueryContext;
import com.example.weatherbot.botapi.handlers.message.BotStateContext;
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
    private final BotStateContext botStateContext;
    private final UserService userService;

    public BotFacade(CallbackQueryContext callbackQueryContext, BotStateContext botStateContext, UserService userService) {
        this.callbackQueryContext = callbackQueryContext;
        this.botStateContext = botStateContext;
        this.userService = userService;
    }

    public SendMessage handleUpdate(Update update){
        SendMessage replyMessage = new SendMessage();

        if(update.hasCallbackQuery()){

            CallbackQuery callbackQuery = update.getCallbackQuery();
            replyMessage = callbackQueryContext.processCallbackQuery(callbackQuery);
        }

        if(update.hasMessage() && update.getMessage().hasText()){

            Message message = update.getMessage();
            replyMessage = handleMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleMessage(Message message){
        String inputMessage = message.getText();
        UserState botState;

        switch (inputMessage) {
            case "/start" -> botState = UserState.START;
            case "/registration" -> botState = UserState.REGISTRATION;
            default -> {

                Optional<User> optionalUser = userService.findUserByChatId(message.getChatId());

                if (optionalUser.isPresent()) {
                    // if user is in database, get his state
                    User user = optionalUser.get();
                    log.info("Processing user {} with state {}", user.getChatId(), user.getUserState());
                    botState = user.getUserState();
                } else {
                    // if user never used bot, set his state to START and call StartHandler
                    log.info("Processing new user {} with state {}", message.getChatId(), UserState.START);
                    botState = UserState.START;
                }
            }
        }

        return botStateContext.processInputMessage(message, botState);
    }
}
