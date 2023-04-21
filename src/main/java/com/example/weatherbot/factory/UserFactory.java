package com.example.weatherbot.factory;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.model.ForecastSchedule;
import com.example.weatherbot.model.User;

public class UserFactory {
    private UserFactory(){}
    public static User getUserWithInBuiltSchedule(Long chatId, UserState userState){
        User user = User.builder()
                .chatId(chatId)
                .userState(userState)
                .apiCalls(0)
                .build();
        ForecastSchedule forecastSchedule = ForecastScheduleFactory.getDisabledForecastSchedule();
        forecastSchedule.setUser(user);
        user.setForecastSchedule(forecastSchedule);

        return user;
    }
}
