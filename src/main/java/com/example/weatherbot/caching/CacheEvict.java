package com.example.weatherbot.caching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheEvict {
    @Scheduled(cron = "${server.weather-cache-evict-cron}")
    @org.springframework.cache.annotation.CacheEvict(value = {"dailyWeatherByName", "dailyWeatherByCoordinates", "forecastByCoordinates", "forecastByName"}, allEntries=true)
    public void clearCache() {
        log.info("Clearing cache...");
    }
}
