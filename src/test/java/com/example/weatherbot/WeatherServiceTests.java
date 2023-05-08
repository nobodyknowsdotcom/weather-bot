package com.example.weatherbot;

import com.example.weatherbot.service.weatherservice.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class WeatherServiceTests extends TestsContext{

    @Autowired
    private WeatherService weatherService;

    @Test
    void getForecastByCoordinatesTest() throws JsonProcessingException {
        Assertions.assertNotNull(weatherService.getForecastByCoordinates(56.83893, 60.6057));
    }

    @Test
    void getGetCoordinatesByLocationNameAndCodeTest() throws JsonProcessingException {
        Assertions.assertNotNull(weatherService.getLocationCoordinatesByNameAndCode("Yekaterinburg"));
    }
}
