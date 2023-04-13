package com.example.weatherbot.service.weatherservice;

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

    public WeatherInfo getWeatherByCoordinates(double lan, double lon) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getWeatherByCoordinates(lan, lon);

        if (response.getStatusCode().value() != 200) {
            throw new ResponseStatusException(response.getStatusCode());
        }
        return new WeatherInfo(response);
    }

    public WeatherInfo getWeatherByName(String name) throws JsonProcessingException {
        ResponseEntity<String> response = weatherApi.getWeatherByName(name);

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