package com.example.weatherbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    START ("Пройди регистрацию, чтобы получить доступ к сервису /registration"),
    START_REGISTRATION("Выбери свой город из списка. Если твоего города нет в списке, пришли мне название своего города или свои координаты"),
    IN_REGISTRATION(""),
    COMPLETE_REGISTRATION("Регистрация пройдена успешно"),
    IDLE(""),
    GOT_FORECAST("Держи прогноз погоды"),
    UNAUTHORIZED("У тебя нету прав для выполнения этой команды. Сначала пройди регистрацию /registration"),
    OUT_OF_QUOTA("У тебя кончилась квота на запросы погоды, нужно ждать следующего дня"),
    FORECAST_BY_COMMAND_NOT_FOUND("Не могу обработать твой запрос, проверь правильность введенных данных"),
    NOT_A_GEO("Это не геолокация"),
    INIT_FORECAST_BY_COMMAND("Пришли название или координаты города, прогноз для которого хочешь получить"),
    FORECAST_BY_COMMAND(""),
    FORECAST_FOR_MY_CITY(""),
    FORECAST_BY_BUTTON("Прогноз на 5 дней"),
    CHANGE_SCHEDULE_SETTINGS("Какие настройки для рассылки вы хотели бы установить?");

    public final String title;
}
