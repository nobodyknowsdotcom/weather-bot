package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.botapi.factory.InlineKeyboardFactory;
import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.mapper.ToMessageMapper;
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
    private final InlineKeyboardFactory inlineKeyboardFactory;

    public ForecastForMyCityHandler(UserService userService, WeatherService weatherService, InlineKeyboardFactory inlineKeyboardFactory) {
        this.userService = userService;
        this.weatherService = weatherService;
        this.inlineKeyboardFactory = inlineKeyboardFactory;
    }

    /**
     * Отправляет пользователю прогноз для его города.
     * Для неавторизованных пользователей вернет сообщение с уведомлением, что необходимо авторизоваться.
     * @param message Сообщение пользователя
     * @return Ответ пользователю с прогнозом на следующие 24 часа с кнопкой для запроса прогноза на несколько дней.
     */
    @Override
    public SendMessage handleMessage(Message message) {
        Optional<User> optionalUser = userService.findUserByChatId(message.getChatId());
        SendMessage sendMessage = new SendMessage();
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
        user.incrementApiCalls();
        userService.updateUser(user);
        userService.updateUserState(user.getChatId(), this.getOutputType());

        String formattedForecast = ToMessageMapper.weatherInfoToMessage(weatherInfo);
        sendMessage.setText(formattedForecast);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyMarkup(inlineKeyboardFactory.getForecastButton(weatherInfo.getCity()));

        log.info("user {} got forecast", message.getChatId());
        return sendMessage;
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
