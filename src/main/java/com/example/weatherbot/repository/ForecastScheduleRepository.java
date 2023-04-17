package com.example.weatherbot.repository;

import com.example.weatherbot.model.ForecastSchedule;
import com.example.weatherbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForecastScheduleRepository extends JpaRepository<ForecastSchedule, Long> {
    Optional<ForecastSchedule> findByUser(User user);
}
