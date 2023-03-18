package com.example.weatherbot.repository;

import com.example.weatherbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByChatId(Long chatId);
    Optional<User> findByChatId(Long chatId);
}
