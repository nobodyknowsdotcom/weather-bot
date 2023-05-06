package com.example.weatherbot.botapi.handlers.callbackquery;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.service.weatherservice.WeatherService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class ForecastByButtonHandler implements CallbackQueryHandler{
    private final WeatherService weatherService;

    public ForecastByButtonHandler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        return new SendMessage(callbackQuery.getMessage().getChatId().toString(), "продам гараж");
    }

    @Override
    public UserState getInputType() {
        return UserState.FORECAST_BY_BUTTON;
    }

    @Override
    public UserState getOutputType() {
        return UserState.IDLE;
    }
}
