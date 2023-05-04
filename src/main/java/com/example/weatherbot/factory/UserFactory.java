package com.example.weatherbot.factory;

import com.example.weatherbot.model.ForecastSchedule;
import com.example.weatherbot.model.User;
import com.example.weatherbot.model.UserStateEntity;

public class UserFactory {
    private UserFactory(){}
    public static User getUserWithInBuiltSchedule(Long chatId, UserStateEntity userStateEntity){
        User user = User.builder()
                .chatId(chatId)
                .userStateEntity(userStateEntity)
                .apiCalls(0)
                .build();
        ForecastSchedule forecastSchedule = ForecastScheduleFactory.getDisabledForecastSchedule();
        forecastSchedule.setUser(user);
        user.setForecastSchedule(forecastSchedule);

        return user;
    }
}
