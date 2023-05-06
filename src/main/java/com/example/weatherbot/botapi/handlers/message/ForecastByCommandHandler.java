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
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
@Slf4j
public class ForecastByCommandHandler implements MessageHandler {
    @Value("${bot.api-quota}")
    private Integer apiQuota;
    private final WeatherService weatherService;
    private final UserService userService;
    private final InlineKeyboardFactory inlineKeyboardFactory;

    public ForecastByCommandHandler(WeatherService weatherService, UserService userService, InlineKeyboardFactory inlineKeyboardFactory) {
        this.weatherService = weatherService;
        this.userService = userService;
        this.inlineKeyboardFactory = inlineKeyboardFactory;
    }

    /**
     * Возвращает прогноз на 1 день по геометке или названию населенного пункта.
     * @param message Сообщение пользователя
     * @return Ответ пользователю с прогнозом на следующие 24 часа с кнопкой для запроса прогноза на несколько дней.
     */
    @Override
    public SendMessage handleMessage(Message message) {
        Optional<User> optionalUser = userService.findUserByChatId(message.getChatId());
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), "");
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
            WeatherInfo weatherInfo;
            if (message.hasLocation()){
                Location location = message.getLocation();
                weatherInfo = weatherService.getDailyWeatherInfoByCoordinates(location.getLatitude(), location.getLongitude());
            } else {
                weatherInfo = weatherService.getDailyWeatherInfoByName(message.getText());
            }
            String formattedForecast = ToMessageMapper.weatherInfoToMessage(weatherInfo);

            user.incrementApiCalls();
            userService.updateUser(user);
            userService.updateUserState(user.getChatId(), this.getOutputType());

            log.info("user {} got forecast", message.getChatId());
            sendMessage.setReplyMarkup(inlineKeyboardFactory.getForecastButton(weatherInfo.getCity()));
            sendMessage.setText(formattedForecast);
        } catch (Exception e){
            log.error(e.getMessage());
            String errorReply = UserState.FORECAST_BY_COMMAND_NOT_FOUND.getTitle();
            sendMessage.setText(errorReply);
        }
        return sendMessage;
    }

    @Override
    public UserState getInputType() {
        return UserState.FORECAST_BY_COMMAND;
    }

    @Override
    public UserState getOutputType() {
        return UserState.IDLE;
    }
}
