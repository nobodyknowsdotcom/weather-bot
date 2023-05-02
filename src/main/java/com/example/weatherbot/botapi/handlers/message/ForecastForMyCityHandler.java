package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.mapper.WeatherMapper;
import com.example.weatherbot.model.User;
import com.example.weatherbot.service.UserService;
import com.example.weatherbot.service.weatherservice.WeatherInfo;
import com.example.weatherbot.service.weatherservice.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
@Slf4j
public class ForecastForMyCityHandler implements MessageHandler{
    @Value("${bot.api-quota}")
    private Integer apiQuota;
    private final UserService userService;
    private final WeatherService weatherService;

    public ForecastForMyCityHandler(UserService userService, WeatherService weatherService) {
        this.userService = userService;
        this.weatherService = weatherService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        Optional<User> optionalUser = userService.findUserByChatId(message.getChatId());
        User user;

        if(optionalUser.isEmpty() || optionalUser.get().getCity() == null){
            return new SendMessage(message.getChatId().toString(), UserState.UNAUTHORIZED.getTitle());
        } else {
            user = optionalUser.get();
        }
        if(user.getApiCalls() >= apiQuota){
            return new SendMessage(message.getChatId().toString(), UserState.OUT_OF_QUOTA.getTitle());
        }

        WeatherInfo weatherInfo;
        try {
            weatherInfo = weatherService.getDailyWeatherInfoByName(user.getCity());
        } catch (Exception e) {
            log.error(e.getMessage());
            String errorReply = UserState.FORECAST_BY_COMMAND_NOT_FOUND.getTitle();
            return new SendMessage(message.getChatId().toString(), errorReply);
        }
        String formattedForecast = WeatherMapper.weatherInfoToMessage(weatherInfo);

        user.incrementApiCalls();
        userService.updateUser(user);

        log.info("user {} got forecast", message.getChatId());
        return new SendMessage(message.getChatId().toString(), formattedForecast);
    }

    @Override
    public UserState getInputType() {
        return UserState.FORECAST_FOR_MY_CITY;
    }

    @Override
    public UserState getOutputType() {
        return UserState.IDLE;
    }
}
