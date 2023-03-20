package com.example.weatherbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    START ("Пройди регистрацию, чтобы получить доступ к сервису -> /registration"),
    REGISTRATION("Введи название своего города"),
    COMPLETE_REGISTRATION("Регистрация пройдена успешно"),
    GOT_FORECAST("Держи прогноз погоды");

    public final String title;
}
