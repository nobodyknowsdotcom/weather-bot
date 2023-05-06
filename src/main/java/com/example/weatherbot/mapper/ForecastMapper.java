package com.example.weatherbot.mapper;

import com.example.weatherbot.service.weatherservice.ForecastInfo;
import com.example.weatherbot.service.weatherservice.WeatherInfo;

import java.util.Comparator;
import java.util.List;

/**
 * OpenApi отдает прогноз на день в виде восьми прогнозов с интервалом три часа,
 * а в них разница между минимальной и максимальной температурой отсутствует
 * (три часа -- слишком короткий промежуток времени, чтобы замерять минимальную и максимальную температуру).
 * Поэтому нужно считать нужные значения среди всех восьми прогнозов, чем и занимается данный класс.
 */
public class ForecastMapper {
    private ForecastMapper(){}
    /**
     * Парсит ForecastInfo в WeatherInfo.
     * Находит среднюю, минимальную и макисмальную температуру по всем WeatherInfo, находящимся в ForecastInfo
     * @param forecastInfo Прогноз, по которому будет составляться WeatherInfo на один день.
     *                     Парсит все значения и находит минимальгную температуру, максимальную и среднюю.
     */
    public static WeatherInfo parseForecastToOneDayWeatherInfo(ForecastInfo forecastInfo){
        WeatherInfo result;
        result = calculateMinMaxAvgTemperature(forecastInfo.getForecast());

        result.setSunriseTimestamp(forecastInfo.getSunrise());
        result.setSunsetTimestamp(forecastInfo.getSunset());
        result.setTimezone(forecastInfo.getTimezone());
        result.setCity(forecastInfo.getCity());

        return result;
    }

    public static WeatherInfo calculateMinMaxAvgTemperature(List<WeatherInfo> weatherInfos){
        WeatherInfo result = weatherInfos.get(0);

        double minTemp = weatherInfos.stream()
                .min(Comparator.comparingDouble(WeatherInfo::getMinTemperature))
                .get()
                .getMinTemperature();
        double maxTemp = weatherInfos.stream()
                .max(Comparator.comparingDouble(WeatherInfo::getMaxTemperature))
                .get()
                .getMaxTemperature();
        double avgTemp = weatherInfos.stream()
                .mapToDouble(WeatherInfo::getTemperature)
                .average()
                .getAsDouble();
        double avgFeltTemp = weatherInfos.stream()
                .mapToDouble(WeatherInfo::getFeltTemperature)
                .average()
                .getAsDouble();

        result.setMinTemperature(minTemp);
        result.setMaxTemperature(maxTemp);
        result.setTemperature(avgTemp);
        result.setFeltTemperature(avgFeltTemp);
        return result;
    }
}
