package com.example.weatherbot.service.weatherservice;

import com.example.weatherbot.weatherapi.WeatherApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WeatherService {
    @Value("${weatherApi.forecastStepInHours}")
    private int forecastStepInHours;
    private final WeatherApi weatherApi;

    public WeatherService(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    /**
     * Считает погоду на следующие 24 часа.
     * Берет несколько прогнозов с интервалом 3 часа, среди них выбирает максимальную и минимальную температуру.
     * Также рассчитывает среднюю температуру
     * @param name Имя города, для которого нужен прогноз
     * @return Прогноз на следующие 24 часа
     * @throws JsonProcessingException Выбрасывается, если не получилось распарсить ответ от openweather
     */
    public WeatherInfo getDailyWeatherInfoByName(String name) throws JsonProcessingException {
        ForecastInfo forecastInfo = getForecastByCityName(name);
        List<WeatherInfo> weatherInfosForOneDay = forecastInfo.getForecast().stream()
                .limit(24/forecastStepInHours)
                .toList();
        forecastInfo.setForecast(weatherInfosForOneDay);

        return ForecastParser.parseForecastToOneDayWeatherInfo(forecastInfo);
    }

    /**
     * Считает погоду на следующие 24 часа.
     * Берет несколько прогнозов с интервалом 3 часа, среди них выбирает максимальную и минимальную температуру.
     * Также рассчитывает среднюю температуру
     * @param lan Широта
     * @param lon Долгота
     * @return Прогноз на следующие 24 часа
     * @throws JsonProcessingException Выбрасывается, если не получилось распарсить ответ от openweather
     */
    public WeatherInfo getDailyWeatherInfoByCoordinates(double lan, double lon) throws JsonProcessingException {
        ForecastInfo forecastInfo = getForecastByCoordinates(lan, lon);
        List<WeatherInfo> weatherInfosForOneDay = forecastInfo.getForecast().stream()
                .limit(24/forecastStepInHours)
                .toList();
        forecastInfo.setForecast(weatherInfosForOneDay);

        return ForecastParser.parseForecastToOneDayWeatherInfo(forecastInfo);
    }

    public WeatherInfo getWeatherByCoordinates(double lan, double lon) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getWeatherByCoordinates(lan, lon);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }
        return new WeatherInfo(response);
    }

    public ForecastInfo getForecastByCoordinates(double lan, double lon) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getForecastByCoordinates(lan, lon);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }
        return new ForecastInfo(response);
    }

    public ForecastInfo getForecastByCityName(String locationName) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getForecastByCityName(locationName);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }
        return new ForecastInfo(response);
    }

    public LocationInfo getLocationCoordinatesByNameAndCode(String name) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getLocationCoordinatesByNameAndCode(name);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }
        return new LocationInfo(response);
    }

    public LocationInfo getCityByCoordinates(Double lat, Double lon) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getReverseLocationByCoordinates(lat, lon);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }
        return new LocationInfo(response);
    }
}