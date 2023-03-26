package com.example.weatherbot.service;

import com.example.weatherbot.model.weather.ForecastInfo;
import com.example.weatherbot.model.weather.LocationInfo;
import com.example.weatherbot.model.weather.WeatherInfo;
import com.example.weatherbot.weatherapi.WeatherApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WeatherService {

    private final WeatherApi weatherApi;

    public WeatherService(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public String getWeatherByCoordinates(double lan, double lon) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getWeatherByCoordinates(lan, lon);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }

        WeatherInfo weatherInfo = new WeatherInfo(response);
        return weatherInfo.toString();
    }

    public String getForecastByCoordinates(double lan, double lon) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getForecastByCoordinates(lan, lon);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }

        ForecastInfo forecastInfo = new ForecastInfo(response);
        return forecastInfo.toString();
    }

    public String getLocationCoordinatesByNameAndCode(String name) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getLocationCoordinatesByNameAndCode(name);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }

        LocationInfo locationInfo = new LocationInfo(response);
        return locationInfo.toString();
    }
}