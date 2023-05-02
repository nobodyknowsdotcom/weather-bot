package com.example.weatherbot.service.weatherservice;

import java.util.Comparator;

/**
 * OpenApi отдает прогноз на день в виде восьми прогнозов с интервалом три часа,
 * а в них разница между минимальной и максимальной температурой отсутствует
 * (три часа -- слишком короткий промежуток времени, чтобы замерять минимальную и максимальную температуру).
 * Поэтому нужно считать нужные значения среди всех восьми прогнозов, чем и занимается данный класс.
 */
public class ForecastParser {
    /**
     * Парсит ForecastInfo в WeatherInfo, находит минимальную, максимальную и среднюю температуру.
     * @param forecastInfo Прогноз, по которому будет составляться WeatherInfo на один день.
     *                     Парсит все значения и находит минимальгную температуру, максимальную и среднюю.
     */
    public static WeatherInfo parseForecastToOneDayWeatherInfo(ForecastInfo forecastInfo){
        WeatherInfo weatherInfo = forecastInfo.getForecast().get(0);

        double minTemp = forecastInfo.getForecast().stream()
                .min(Comparator.comparingDouble(WeatherInfo::getMinTemperature))
                .get()
                .getMinTemperature();
        double maxTemp = forecastInfo.getForecast().stream()
                .max(Comparator.comparingDouble(WeatherInfo::getMaxTemperature))
                .get()
                .getMaxTemperature();
        double avgTemp = forecastInfo.getForecast().stream()
                .mapToDouble(WeatherInfo::getTemperature)
                .average()
                .getAsDouble();
        double avgFeltTemp = forecastInfo.getForecast().stream()
                .mapToDouble(WeatherInfo::getFeltTemperature)
                .average()
                .getAsDouble();

        weatherInfo.setMinTemperature(minTemp);
        weatherInfo.setMaxTemperature(maxTemp);
        weatherInfo.setTemperature(avgTemp);
        weatherInfo.setFeltTemperature(avgFeltTemp);

        weatherInfo.setSunriseTimestamp(forecastInfo.getSunrise());
        weatherInfo.setSunsetTimestamp(forecastInfo.getSunset());
        weatherInfo.setTimezone(forecastInfo.getTimezone());

        return weatherInfo;
    }
}
