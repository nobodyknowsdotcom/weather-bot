package com.example.weatherbot;

import com.example.weatherbot.weatherapi.WeatherApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class WeatherApiTests extends TestsContext{
    @Autowired
    private WeatherApi weatherApi;

    @Test
    void getWeatherByCoordinatesTest() {
        Assertions.assertNotNull(weatherApi.getWeatherByCoordinates(56.83893, 60.6057));
    }

    @Test
    void getForecastByCoordinatesTest() {
        Assertions.assertNotNull(weatherApi.getForecastByCoordinates(56.83893, 60.6057));
    }

    @Test
    void getGetCoordinatesByLocationNameAndCodeTest() {
        Assertions.assertNotNull(weatherApi.getLocationCoordinatesByNameAndCode("Yekaterinburg"));
    }
}
