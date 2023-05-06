package com.example.weatherbot.botapi.factory;

import com.example.weatherbot.enums.Emoji;
import com.example.weatherbot.enums.UserState;
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
    public InlineKeyboardMarkup getPopularCitiesWithChosenCity(String city){
        InlineKeyboardMarkup mockMarkup = getPopularCitiesInlineKeyboard();
        List<List<InlineKeyboardButton>> newMarkup = new ArrayList<>();

        mockMarkup.getKeyboard()
                .forEach( i -> {
                    if (i.get(0).getCallbackData().contains(city)) {
                        newMarkup.add(getCheckedCityButton(city));
                    } else {
                        newMarkup.add(i);
                    }
                }
        );

        return new InlineKeyboardMarkup(newMarkup);
    }

    /**
     * Фабричный метод. Возвращает кнопку для получения прогноза на заданное количество дней.
     * Количество дней для прогноза кладет в callbackData по схеме 'forecast=<количество дней>'
     * @param city Город, по которому будет взят прогноз
     * @return Кнопка с встроенной CallbackData
     */
    public InlineKeyboardMarkup getForecastButton(String city){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton(UserState.FORECAST_BY_BUTTON.getTitle());
        button.setCallbackData(String.format("forecast=%s", city));

        buttonRow.add(button);
        buttons.add(buttonRow);
        markup.setKeyboard(buttons);
        return markup;
    }
    private List<InlineKeyboardButton> getCityAsButton(String city){
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton(city);
        button.setCallbackData(String.format("city=%s", city));
        buttonRow.add(button);

        return buttonRow;
    }
    private List<InlineKeyboardButton> getCheckedCityButton(String city){
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton(city + " " + Emoji.CHECKED.getText());
        button.setCallbackData(String.format("city=%s", city));
        buttonRow.add(button);

        return buttonRow;
    }
}
