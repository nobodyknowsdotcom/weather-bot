package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.model.ForecastSchedule;
import com.example.weatherbot.model.User;
import com.example.weatherbot.service.UserService;
import com.example.weatherbot.service.forecastscheduleservice.ForecastScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
@Slf4j
public class ScheduleChangeHandler implements MessageHandler {

    private final UserService userService;
    private final ForecastScheduleService forecastScheduleService;

    public ScheduleChangeHandler(UserService userService, ForecastScheduleService forecastScheduleService) {
        this.userService = userService;
        this.forecastScheduleService = forecastScheduleService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        Optional<User> optionalUser = userService.findUserByChatId(message.getChatId());
        User user;
        ForecastSchedule forecastSchedule;

        if (optionalUser.isEmpty() || optionalUser.get().getCity() == null) {
            return new SendMessage(message.getChatId().toString(), UserState.UNAUTHORIZED.getTitle());
        } else {
            user = optionalUser.get();
        }

        user.setUserState(UserState.IDLE);
        userService.updateUser(user);

        forecastSchedule = user.getForecastSchedule();
        forecastSchedule.setIsScheduleEnabled(!forecastSchedule.getIsScheduleEnabled());

        String answer;

        if (forecastSchedule.getIsScheduleEnabled()) {
            answer = "Вы включили ежедневную рассылку прогноза погоды";
        } else {
            answer = "Вы отключили ежедневную рассылку прогноза погоды";
        }
        forecastScheduleService.save(forecastSchedule);

        return new SendMessage(message.getChatId().toString(), answer);
    }

    @Override
    public UserState getInputType() {
        return UserState.CHANGE_SCHEDULE_SETTINGS;
    }

    @Override
    public UserState getOutputType() {
        return UserState.IDLE;
    }
}
