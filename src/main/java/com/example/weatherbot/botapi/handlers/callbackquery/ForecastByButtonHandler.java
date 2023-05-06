package com.example.weatherbot.botapi.handlers.callbackquery;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.mapper.ToMessageMapper;
import com.example.weatherbot.model.User;
import com.example.weatherbot.service.UserService;
import com.example.weatherbot.service.weatherservice.ForecastInfo;
import com.example.weatherbot.service.weatherservice.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

@Service
@Slf4j
public class ForecastByButtonHandler implements CallbackQueryHandler{
    @Value("${bot.api-quota}")
    private Integer apiQuota;
    private final WeatherService weatherService;
    private final UserService userService;

    public ForecastByButtonHandler(WeatherService weatherService, UserService userService) {
        this.weatherService = weatherService;
        this.userService = userService;
    }

    /**
     * Возвращает прогноз на несколько дней.
     * @param callbackQuery CallbackQuery, вытянутый из сообщения пользователя.
     *                      Сожержит в себе название города, по которому нужно вернуть прогноз.
     * @return Ответ пользователю с прогнозом на несколько дней.
     */
    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Optional<User> optionalUser = userService.findUserByChatId(chatId);
        SendMessage sendMessage = new SendMessage();
        User user;

        if(optionalUser.isEmpty()){
            return new SendMessage(chatId.toString(), UserState.UNAUTHORIZED.getTitle());
        } else {
            user = optionalUser.get();
        }
        if(user.getApiCalls() >= apiQuota){
            return new SendMessage(chatId.toString(), UserState.OUT_OF_QUOTA.getTitle());
        }

        ForecastInfo forecastInfo;
        try {
            forecastInfo = weatherService.getDailyForecastInfoByName(user.getCity());
        } catch (Exception e) {
            log.error(e.getMessage());
            String errorReply = UserState.FORECAST_BY_COMMAND_NOT_FOUND.getTitle();
            return new SendMessage(chatId.toString(), errorReply);
        }
        user.incrementApiCalls();
        userService.updateUser(user);
        userService.updateUserState(user.getChatId(), this.getOutputType());

        String formattedForecast = ToMessageMapper.forecastInfoToMessage(forecastInfo);
        sendMessage.setText(formattedForecast);
        sendMessage.setChatId(chatId);

        log.info("user {} got forecast by callback button", chatId);
        return sendMessage;
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
