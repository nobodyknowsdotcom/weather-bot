package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.model.User;
import com.example.weatherbot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
@Slf4j
public class ForecastForCityInitHandler implements MessageHandler{
    private final UserService userService;

    public ForecastForCityInitHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        Optional<User> user = userService.findUserByChatId(message.getChatId());

        if(user.isEmpty()){
            return new SendMessage(message.getChatId().toString(), UserState.UNAUTHORIZED.getTitle());
        }

        userService.updateUserState(message.getChatId(), this.getOutputType());
        return new SendMessage(message.getChatId().toString(), this.getHandlerType().getTitle());
    }

    @Override
    public UserState getHandlerType() {
        return UserState.INIT_FORECAST_FOR_CITY;
    }

    @Override
    public UserState getOutputType() {
        return UserState.FORECAST_FOR_CITY;
    }
}
