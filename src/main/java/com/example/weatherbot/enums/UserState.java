package com.example.weatherbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    START ("Пройди регистрацию, чтобы получить доступ к сервису /registration"),
    START_REGISTRATION("Выбери свой город из списка. Если твоего города нет в списке, пришли мне название своего города или свои координаты"),
    IN_REGISTRATION(""),
    FORECAST_FOR_CITY(""),
    COMPLETE_REGISTRATION("Регистрация пройдена успешно"),
    IDLE(""),
    GOT_FORECAST("Держи прогноз погоды"),
    UNAUTHORIZED("У тебя нету прав для выполнения этой команды. Сначала пройди регистрацию /registration"),
    OUT_OF_QUOTA("У тебя кончилась квота на запросы погоды, нужно ждать следующего дня"),
    FORECAST_NOT_FOUND("Прогноз для города %s не найден. Проверь корректность названия города"),
    INIT_FORECAST_FOR_CITY("Введи название города, прогноз для которого хочешь получить"),
    CHANGE_SCHEDULE_SETTINGS("Какие настройки для рассылки вы хотели бы установить?");

    public final String title;
}
