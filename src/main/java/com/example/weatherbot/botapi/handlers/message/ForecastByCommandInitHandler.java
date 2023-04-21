package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.model.User;
import com.example.weatherbot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
public class ForecastByCommandInitHandler implements MessageHandler{
    private final UserService userService;

    public ForecastByCommandInitHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        Optional<User> user = userService.findUserByChatId(message.getChatId());

        if(user.isEmpty()){
            return new SendMessage(message.getChatId().toString(), UserState.UNAUTHORIZED.getTitle());
        }

        userService.updateUserState(message.getChatId(), this.getOutputType());
        return new SendMessage(message.getChatId().toString(), this.getInputType().getTitle());
    }

    @Override
    public UserState getInputType() {
        return UserState.INIT_FORECAST_BY_COMMAND;
    }

    @Override
    public UserState getOutputType() {
        return UserState.FORECAST_BY_COMMAND;
    }
}
