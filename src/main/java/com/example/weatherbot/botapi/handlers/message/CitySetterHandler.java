package com.example.weatherbot.botapi.handlers.message;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.service.UserService;
import com.example.weatherbot.service.weatherservice.LocationInfo;
import com.example.weatherbot.service.weatherservice.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
public class CitySetterHandler implements MessageHandler{
    private final WeatherService weatherService;
    private final UserService userService;

    public CitySetterHandler(WeatherService weatherService, UserService userService) {
        this.weatherService = weatherService;
        this.userService = userService;
    }

    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage messageToSend = new SendMessage();
        messageToSend.setChatId(message.getChatId());

        if (message.hasLocation()){
            Location userLocation = message.getLocation();

            try{
                LocationInfo locationInfo = weatherService.
                        getCityByCoordinates(userLocation.getLatitude(), userLocation.getLongitude());

                userService.updateUserCity(message.getChatId(), locationInfo.getLocationName());
                userService.updateUserState(message.getChatId(), UserState.COMPLETE_REGISTRATION);

                messageToSend.setText(String.format("%s, твой город -- %s",
                        UserState.COMPLETE_REGISTRATION.getTitle(), locationInfo.getLocationName()));
            } catch (Exception e) {
                log.info(e.getMessage());
                messageToSend.setText("Не могу распарсить твою локацию, попробуй ввести город вручную или выбрать из списка.");
            }
        } else {
            try{
                LocationInfo locationInfo = weatherService.getLocationCoordinatesByNameAndCode(message.getText());

                userService.updateUserCity(message.getChatId(), locationInfo.getLocationName());
                userService.updateUserState(message.getChatId(), UserState.COMPLETE_REGISTRATION);

                messageToSend.setText(String.format("%s, твой город -- %s",
                        UserState.COMPLETE_REGISTRATION.getTitle(), locationInfo.getLocationName()));
            } catch (Exception e) {
                log.error(e.getMessage());
                messageToSend.setText("Такого города не существует, попробуй еще раз.");
            }
        }

        return messageToSend;
    }

    @Override
    public UserState getHandlerType() {
        return UserState.IN_REGISTRATION;
    }
}
