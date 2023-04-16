package com.example.weatherbot.service.forecastscheduleservice;

import com.example.weatherbot.model.ForecastSchedule;
import com.example.weatherbot.model.User;
import com.example.weatherbot.repository.ForecastScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForecastScheduleService {
    private final ForecastScheduleRepository forecastScheduleRepository;

    public ForecastScheduleService(ForecastScheduleRepository forecastScheduleRepository) {
        this.forecastScheduleRepository = forecastScheduleRepository;
    }

    public Optional<ForecastSchedule> findByUser(User user) {
        return forecastScheduleRepository.findByUser(user);
    }

    public ForecastSchedule save(ForecastSchedule forecastSchedule) {
        return forecastScheduleRepository.save(forecastSchedule);
    }
}
