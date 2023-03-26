package com.example.weatherbot.service.weatherservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;

@Getter
@ToString
public class ForecastInfo {
    private final ArrayList<WeatherInfo> forecast = new ArrayList<>();

    protected ForecastInfo(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        for (JsonNode node : jsonNode.get("list")) {
            forecast.add(new WeatherInfo(node));
        }
    }
}