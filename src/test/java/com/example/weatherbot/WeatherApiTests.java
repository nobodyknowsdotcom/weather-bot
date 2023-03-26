package com.example.weatherbot;

import com.example.weatherbot.weatherapi.WeatherApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class WeatherApiTests {
    @Autowired
    private WeatherApi weatherApi;

    @Test
    void getWeatherByCoordinatesTest() {
        System.out.println(weatherApi.getWeatherByCoordinates(56.83893, 60.6057));
    }

    @Test
    void getForecastByCoordinatesTest() {
        System.out.println(weatherApi.getForecastByCoordinates(56.83893, 60.6057));
    }

    @Test
    void getGetCoordinatesByLocationNameAndCodeTest() {
        System.out.println(weatherApi.getLocationCoordinatesByNameAndCode("Yekaterinburg"));
    }
}
