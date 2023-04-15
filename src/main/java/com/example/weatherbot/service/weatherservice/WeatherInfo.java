package com.example.weatherbot.service.weatherservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

@Getter
@ToString
public class WeatherInfo {
    //https://openweathermap.org/weather-data
    //temperature in Celsius
    private final double temperature;
    //felt temperature
    private final double feltTemperature;
    //minimal temperature
    private final double minTemperature;
    //maximal temperature
    private final double maxTemperature;
    //pressure in hPa
    private final int pressure;
    //humidity in percents
    private final int humidity;
    //wind speed in m/s
    private final int windSpeed;
    //cloudiness in percents
    private final int cloudiness;
    //timestamp
    private final int dt;
    //weather condition, more info on https://openweathermap.org/weather-conditions
    private final int conditionId;
    //description of weather condition
    private final String conditionDescription;
    //sunrise timestamp
    private int sunriseTimestamp;
    private int sunsetTimestamp;

    protected WeatherInfo(ResponseEntity<String> response) throws JsonProcessingException {
        this(new ObjectMapper().readTree(response.getBody()));
    }

    protected WeatherInfo(JsonNode jsonNode) {
        temperature = jsonNode.get("main").get("temp").asDouble();
        feltTemperature = jsonNode.get("main").get("feels_like").asDouble();
        minTemperature = jsonNode.get("main").get("temp_min").asDouble();
        maxTemperature = jsonNode.get("main").get("temp_max").asDouble();
        pressure = jsonNode.get("main").get("pressure").asInt();
        humidity = jsonNode.get("main").get("humidity").asInt();

        conditionId = jsonNode.get("weather").get(0).get("id").asInt();
        conditionDescription = jsonNode.get("weather").get(0).get("description").asText();

        windSpeed = jsonNode.get("wind").get("speed").asInt();
        cloudiness = jsonNode.get("clouds").get("all").asInt();

        dt = jsonNode.get("dt").asInt();

        try{
            sunriseTimestamp = jsonNode.get("sys").get("sunrise").asInt();
            sunsetTimestamp = jsonNode.get("sys").get("sunset").asInt();
        } catch (Exception e){}
    }
}