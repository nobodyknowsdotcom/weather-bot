package com.example.weatherbot.model.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

@Getter
@ToString
public class LocationInfo {
    private final String locationName;
    private final double lat;
    private final double lon;
    private final String countryCode;
    public LocationInfo(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody()).get(0);
        locationName = jsonNode.get("name").asText();
        lat = jsonNode.get("lat").asDouble();
        lon = jsonNode.get("lon").asDouble();
        countryCode = jsonNode.get("country").asText();
    }
}