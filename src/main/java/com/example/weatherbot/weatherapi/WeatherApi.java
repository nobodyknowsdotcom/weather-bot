package com.example.weatherbot.weatherapi;

import com.example.weatherbot.config.WeatherApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class WeatherApi {

    public static final String APPID = "appid";
    public static final String UNITS = "units";
    public static final String GET = "GET {}";
    private final WeatherApiProperties weatherApiProperties;
    private final RestTemplate restTemplate;

    public WeatherApi(WeatherApiProperties weatherApiProperties) {
        this.weatherApiProperties = weatherApiProperties;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getWeatherByCoordinates(double lat, double lon) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(weatherApiProperties.getProtocol())
                .host(weatherApiProperties.getHost())
                .path("/data/2.5/weather")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("lang", "ru")
                .queryParam(APPID, weatherApiProperties.getToken())
                .queryParam(UNITS, weatherApiProperties.getUnits())
                .encode()
                .build()
                .toUri();

        log.info(GET, uri);
        return restTemplate.getForEntity(uri, String.class);
    }

    public ResponseEntity<String> getWeatherByName(String name) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(weatherApiProperties.getProtocol())
                .host(weatherApiProperties.getHost())
                .path("/data/2.5/weather")
                .queryParam("q", name)
                .queryParam("lang", "ru")
                .queryParam(APPID, weatherApiProperties.getToken())
                .queryParam(UNITS, weatherApiProperties.getUnits())
                .encode()
                .build()
                .toUri();

        log.info(GET, uri);
        return restTemplate.getForEntity(uri, String.class);
    }

    public ResponseEntity<String> getForecastByCoordinates(double latitude, double longitude) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(weatherApiProperties.getProtocol())
                .host(weatherApiProperties.getHost())
                .path("/data/2.5/forecast")
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("lang", "ru")
                .queryParam(APPID, weatherApiProperties.getToken())
                .queryParam(UNITS, weatherApiProperties.getUnits())
                .encode()
                .build()
                .toUri();

        log.info(GET, uri);
        return restTemplate.getForEntity(uri, String.class);
    }

    public ResponseEntity<String> getForecastByCityName(String locationName){
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(weatherApiProperties.getProtocol())
                .host(weatherApiProperties.getHost())
                .path("/data/2.5/forecast")
                .queryParam("q", locationName)
                .queryParam("lang", "ru")
                .queryParam(APPID, weatherApiProperties.getToken())
                .queryParam(UNITS, weatherApiProperties.getUnits())
                .encode()
                .build()
                .toUri();

        log.info(GET, uri);
        return restTemplate.getForEntity(uri, String.class);
    }

    public ResponseEntity<String> getLocationCoordinatesByNameAndCode(String locationName) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(weatherApiProperties.getProtocol())
                .host(weatherApiProperties.getHost())
                .path("/geo/1.0/direct")
                .queryParam("q", locationName)
                .queryParam("limit", 1)
                .queryParam("lang", "ru")
                .queryParam(APPID, weatherApiProperties.getToken())
                .queryParam(UNITS, weatherApiProperties.getUnits())
                .encode()
                .build()
                .toUri();

        log.info(GET, uri);
        return restTemplate.getForEntity(uri, String.class);
    }

    public ResponseEntity<String> getReverseLocationByCoordinates(Double lat, Double lon){
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(weatherApiProperties.getProtocol())
                .host(weatherApiProperties.getHost())
                .path("/geo/1.0/reverse")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("limit", 1)
                .queryParam(APPID, weatherApiProperties.getToken())
                .queryParam(UNITS, weatherApiProperties.getUnits())
                .encode()
                .build()
                .toUri();

        log.info(GET, uri);
        return restTemplate.getForEntity(uri, String.class);
    }
}
