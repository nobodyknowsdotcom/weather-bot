package com.example.weatherbot.service.weatherservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@ToString
public class WeatherInfo {
    //https://openweathermap.org/weather-data
    //temperature in Celsius
    private double temperature;
    //felt temperature
    private double feltTemperature;
    //minimal temperature
    private double minTemperature;
    //maximal temperature
    private double maxTemperature;
    //pressure in hPa
    private int pressure;
    //humidity in percents
    private int humidity;
    //wind speed in m/s
    private int windSpeed;
    //cloudiness in percents
    private int cloudiness;
    //timestamp
    private long date;
    //weather condition, more info on https://openweathermap.org/weather-conditions
    private int conditionId;
    //description of weather condition
    private String conditionDescription;
    //sunrise timestamp
    private long sunriseTimestamp;
    private long sunsetTimestamp;
    private int timezone;

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

        date = jsonNode.get("dt").asLong();

        try{
            sunriseTimestamp = jsonNode.get("sys").get("sunrise").asLong();
            sunsetTimestamp = jsonNode.get("sys").get("sunset").asLong();
            timezone = jsonNode.get("sunset").asInt();
        } catch (Exception e) {}
    }
}