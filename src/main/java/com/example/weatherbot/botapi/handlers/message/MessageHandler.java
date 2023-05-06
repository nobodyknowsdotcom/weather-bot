package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {
    /**
     * Обрабатывает сообщение пользователя, отправляет ответ на каждое его сообщение.
     * Ответ может быть пустым, если пользователю не нужно отвечать.
     * @param message Сообщение пользователя
     * @return Ответ на сообщение пользователя
     */
    SendMessage handleMessage(Message message);

    /**
     * @return UserState, обработчиком для которого является данный класс
     */
    UserState getInputType();

    /**
     * @return UserState, присваемый пользователю после обработки входящего запроса
     */
    UserState getOutputType();
}
