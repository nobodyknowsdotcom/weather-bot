package com.example.weatherbot.factory;

import com.example.weatherbot.enums.Emoji;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardFactory {
    @Value("#{'${bot.most-popular-cities}'.split(',')}")
    private List<String> cities;
    public InlineKeyboardMarkup getPopularCitiesInlineKeyboard(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> citiesButtons = new ArrayList<>();
        cities.forEach(x -> citiesButtons.add(getCityAsButton(x)));

        markup.setKeyboard(citiesButtons);

        return markup;
    }

    private List<InlineKeyboardButton> getCityAsButton(String city){
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton(city + " " + Emoji.CITY.getText());
        button.setCallbackData(String.format("city=%s", city));
        buttonRow.add(button);

        return buttonRow;
    }
}
