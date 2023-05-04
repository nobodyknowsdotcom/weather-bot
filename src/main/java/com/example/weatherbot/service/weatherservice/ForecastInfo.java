package com.example.weatherbot.service.weatherservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ForecastInfo {
    private List<WeatherInfo> forecast = new ArrayList<>();
    private Integer timezone;
    private Long sunrise;
    private Long sunset;

    protected ForecastInfo(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        for (JsonNode node : jsonNode.get("list")) {
            forecast.add(new WeatherInfo(node));
        }

        this.timezone = jsonNode.get("city").get("timezone").asInt();
        this.sunrise = jsonNode.get("city").get("sunrise").asLong();
        this.sunset = jsonNode.get("city").get("sunset").asLong();
    }
}