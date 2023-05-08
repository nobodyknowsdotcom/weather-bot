package com.example.weatherbot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WeatherApiProperties {
    @Value("${weatherApi.protocol}")
    private String protocol;
    @Value("${weatherApi.host}")
    private String host;
    @Value("${weatherApi.weatherEndpoint}")
    private String weatherEndpoint;
    @Value("${weatherApi.forecastEndpoint}")
    private String forecastEndpoint;
    @Value("${weatherApi.geoEndpoint}")
    private String geoEndpoint;
    @Value("${weatherApi.token}")
    private String token;
    @Value("${weatherApi.units}")
    private String units;
}
