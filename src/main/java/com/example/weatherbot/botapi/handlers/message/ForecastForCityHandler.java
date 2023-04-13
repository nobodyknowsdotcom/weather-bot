package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.mapper.WeatherMapper;
import com.example.weatherbot.model.User;
import com.example.weatherbot.service.UserService;
import com.example.weatherbot.service.weatherservice.WeatherInfo;
import com.example.weatherbot.service.weatherservice.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
@Slf4j
public class ForecastForCityHandler implements MessageHandler{
    @Value("${bot.api-quota}")
    private Integer apiQuota;
    private final WeatherService weatherService;
    private final UserService userService;

    public ForecastForCityHandler(WeatherService weatherService, UserService userService) {
        this.weatherService = weatherService;
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        Optional<User> optionalUser = userService.findUserByChatId(message.getChatId());
        User user;

        if(optionalUser.isEmpty()){
            return new SendMessage(message.getChatId().toString(), UserState.UNAUTHORIZED.getTitle());
        } else {
            user = optionalUser.get();
        }

        if(user.getApiCalls() >= apiQuota){
            return new SendMessage(message.getChatId().toString(), UserState.OUT_OF_QUOTA.getTitle());
        }

        try {
            WeatherInfo weatherInfo = weatherService.getWeatherByName(message.getText());
            String formattedForecast = WeatherMapper.weatherInfoToMessage(weatherInfo);

            user.incrementApiCalls();
            userService.updateUser(user);

            log.info("user {} got forecast", message.getChatId());
            return new SendMessage(message.getChatId().toString(), formattedForecast);
        } catch (Exception e){
            log.error(e.getMessage());
            String errorReply = String.format(UserState.FORECAST_NOT_FOUND.getTitle(), message.getText());
            return new SendMessage(message.getChatId().toString(), errorReply);
        }
    }

    @Override
    public UserState getHandlerType() {
        return UserState.FORECAST_FOR_CITY;
    }

    @Override
    public UserState getOutputType() {
        return UserState.IDLE;
    }
}
