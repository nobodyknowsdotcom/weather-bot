package com.example.weatherbot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WeatherApiProperties {
    @Value("${weatherApi.token}")
    private String token;
    @Value("${weatherApi.units}")
    private String units;
}
