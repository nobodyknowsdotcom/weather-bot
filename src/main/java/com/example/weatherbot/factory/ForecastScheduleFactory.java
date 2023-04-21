package com.example.weatherbot.factory;

import com.example.weatherbot.model.ForecastSchedule;

public class ForecastScheduleFactory {
    private ForecastScheduleFactory(){}
    public static ForecastSchedule getDisabledForecastSchedule(){
        return ForecastSchedule.builder()
                .isScheduleEnabled(false)
                .build();
    }
}
