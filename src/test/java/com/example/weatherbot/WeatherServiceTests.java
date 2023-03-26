package com.example.weatherbot;

import com.example.weatherbot.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class WeatherServiceTests {

    @Autowired
    private WeatherService weatherService;

    @Test
    void getWeatherByCoordinatesTest() throws JsonProcessingException {
        System.out.println(weatherService.getWeatherByCoordinates(56.83893, 60.6057));
    }

    @Test
    void getForecastByCoordinatesTest() throws JsonProcessingException {
        System.out.println(weatherService.getForecastByCoordinates(56.83893, 60.6057));
    }

    @Test
    void getGetCoordinatesByLocationNameAndCodeTest() throws JsonProcessingException {
        System.out.println(weatherService.getLocationCoordinatesByNameAndCode("Yekaterinburg"));
    }
}
