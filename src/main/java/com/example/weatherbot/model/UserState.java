package com.example.weatherbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    START ("Пройди регистрацию, чтобы получить доступ к сервису"),
    REGISTRATION("Введи свой город"),
    COMPLETE_REGISTRATION("Регистрация пройдена успешно"),
    GOT_FORECAST("Держи прогноз погоды");

    public final String title;
}
