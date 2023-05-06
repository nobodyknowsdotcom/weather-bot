package com.example.weatherbot.service.forecastscheduleservice;

import com.example.weatherbot.botapi.WeatherBot;
import com.example.weatherbot.mapper.ToMessageMapper;
import com.example.weatherbot.model.User;
import com.example.weatherbot.repository.UserRepository;
import com.example.weatherbot.service.weatherservice.WeatherInfo;
import com.example.weatherbot.service.weatherservice.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduledForecastSending {

    private UserRepository userRepository;
    private WeatherBot weatherBot;
    private WeatherService weatherService;

    @Scheduled(cron = "${bot.schedule-cron}")
    public void sendScheduledForecast() {
        List<User> users = userRepository.findAll().stream().filter(user -> user.getForecastSchedule().getIsScheduleEnabled()).toList();
        for (User user : users) {
            WeatherInfo weatherInfo = null;
            try {
                weatherInfo = weatherService.getDailyWeatherInfoByName(user.getCity());
            } catch (JsonProcessingException e) {
                continue;
            }
            String formattedForecast = ToMessageMapper.weatherInfoToMessage(weatherInfo);
            log.info("sending scheduled message for user {}", user);
            weatherBot.sendMessage(user.getChatId(), formattedForecast);
        }
    }
}
