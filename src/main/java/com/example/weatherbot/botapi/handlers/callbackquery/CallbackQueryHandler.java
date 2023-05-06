package com.example.weatherbot.botapi.handlers.callbackquery;

import com.example.weatherbot.enums.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryHandler {
    /**
     * Обрабатывает входящую CallbackData, возвращает ответ пользователю.
     * @param callbackQuery CallbackQuery, вытянутый из сообщения пользователя
     * @return Ответ пользователю
     */
    SendMessage handle(CallbackQuery callbackQuery);

    /**
     * @return UserState, обрабатываемый данным обработчиком
     */
    UserState getInputType();

    /**
     * @return UserState, присваемый пользователю после обработки входящего запроса
     */
    UserState getOutputType();
}
