package com.example.weatherbot.service.weatherservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherInfo {
    //https://openweathermap.org/weather-data
    //temperature in Celsius
    double temperature;
    //felt temperature
    double feltTemperature;
    //minimal temperature
    double minTemperature;
    //maximal temperature
    double maxTemperature;
    //pressure in hPa
    int pressure;
    //humidity in percents
    int humidity;
    //wind speed in m/s
    int windSpeed;
    //cloudiness in percents
    int cloudiness;
    //timestamp
    long date;
    //weather condition, more info on https://openweathermap.org/weather-conditions
    int conditionId;
    //description of weather condition
    String conditionDescription;
    //sunrise timestamp
    long sunriseTimestamp;
    long sunsetTimestamp;
    int timezone;
    String city;

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