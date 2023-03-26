package com.example.weatherbot.weatherapi;

import com.example.weatherbot.config.WeatherApiProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherApi {

    private final WeatherApiProperties weatherApiProperties;
    private final RestTemplate restTemplate;

    public WeatherApi(WeatherApiProperties weatherApiProperties) {
        this.weatherApiProperties = weatherApiProperties;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getWeatherByCoordinates(double lat, double lon) {
        String uri = UriComponentsBuilder.newInstance()
                .scheme("https").host("api.openweathermap.org").path("/data/2.5/weather")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", weatherApiProperties.getToken())
                .queryParam("units", weatherApiProperties.getUnits())
                .encode()
                .toUriString();

        return restTemplate.getForEntity(uri, String.class);
    }

    public ResponseEntity<String> getForecastByCoordinates(double latitude, double longtitude) {
        String uri = UriComponentsBuilder.newInstance()
                .scheme("https").host("api.openweathermap.org").path("/data/2.5/forecast")
                .queryParam("lat", latitude)
                .queryParam("lon", longtitude)
                .queryParam("appid", weatherApiProperties.getToken())
                .queryParam("units", weatherApiProperties.getUnits())
                .encode()
                .toUriString();

        return restTemplate.getForEntity(uri, String.class);
    }

    public ResponseEntity<String> getLocationCoordinatesByNameAndCode(String locationName) {
        String uri = UriComponentsBuilder.newInstance()
                .scheme("https").host("api.openweathermap.org").path("/geo/1.0/direct")
                .queryParam("q", locationName)
                .queryParam("limit", 1)
                .queryParam("appid", weatherApiProperties.getToken())
                .queryParam("units", weatherApiProperties.getUnits())
                .encode()
                .toUriString();

        return restTemplate.getForEntity(uri, String.class);
    }
}
