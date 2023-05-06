package com.example.weatherbot.service.weatherservice;

import com.example.weatherbot.mapper.ForecastMapper;
import com.example.weatherbot.weatherapi.WeatherApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WeatherService {
    @Value("${weatherApi.forecastStepInHours}")
    private int forecastStepInHours;
    @Value("${weatherApi.timestamps-in-api-response}")
    private int timestampsCount;
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
    @Cacheable(value = "dailyWeatherByName")
    public WeatherInfo getDailyWeatherInfoByName(String name) throws JsonProcessingException {
        ForecastInfo forecastInfo = getForecastByCityName(name);
        List<WeatherInfo> weatherInfosForOneDay = forecastInfo.getForecast().stream()
                .limit(24/forecastStepInHours)
                .toList();
        forecastInfo.setForecast(weatherInfosForOneDay);

        return ForecastMapper.parseForecastToOneDayWeatherInfo(forecastInfo);
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
    @Cacheable(value = "dailyWeatherByCoordinates")
    public WeatherInfo getDailyWeatherInfoByCoordinates(double lan, double lon) throws JsonProcessingException {
        ForecastInfo forecastInfo = getForecastByCoordinates(lan, lon);
        List<WeatherInfo> weatherInfosForOneDay = forecastInfo.getForecast().stream()
                .limit(24/forecastStepInHours)
                .toList();
        forecastInfo.setForecast(weatherInfosForOneDay);

        return ForecastMapper.parseForecastToOneDayWeatherInfo(forecastInfo);
    }

    /**
     * Обращается к апи, обрабатывает ответ от апи и возвращает прогноз на 5 дней.
     * @param city Имя города
     * @return Прогноз с подсчитанной минимальной, максимальной и средней температурой
     */
    public ForecastInfo getDailyForecastInfoByName(String city) throws JsonProcessingException {
        ForecastInfo forecastInfo = getForecastByCityName(city);

        List<WeatherInfo> calculatedWeatherInfo = new ArrayList<>();
        int timestampsInOneDay = (24/forecastStepInHours);
        int daysInForecast = timestampsCount/timestampsInOneDay;

        for (int i = 0; i<daysInForecast; i += 1){
            List<WeatherInfo> oneDayWeatherInfo = forecastInfo.getForecast()
                    .subList(i*timestampsInOneDay, (i+1)*timestampsInOneDay);
            calculatedWeatherInfo.add(ForecastMapper.calculateMinMaxAvgTemperature(oneDayWeatherInfo));
        }
        forecastInfo.setForecast(calculatedWeatherInfo);

        return forecastInfo;
    }
    @Cacheable(value = "forecastByCoordinates")
    public ForecastInfo getForecastByCoordinates(double lan, double lon) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getForecastByCoordinates(lan, lon);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }
        return new ForecastInfo(response);
    }
    @Cacheable(value = "forecastByName")
    public ForecastInfo getForecastByCityName(String locationName) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getForecastByCityName(locationName);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }
        return new ForecastInfo(response);
    }
    @Cacheable(value = "locationByName")
    public LocationInfo getLocationCoordinatesByNameAndCode(String name) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getLocationCoordinatesByNameAndCode(name);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }
        return new LocationInfo(response);
    }
    @Cacheable(value = "cityByCoordinates")
    public LocationInfo getCityByCoordinates(Double lat, Double lon) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getReverseLocationByCoordinates(lat, lon);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }
        return new LocationInfo(response);
    }
}