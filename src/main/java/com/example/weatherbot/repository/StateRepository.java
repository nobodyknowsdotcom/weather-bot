package com.example.weatherbot.repository;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.model.UserStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<UserStateEntity, Long> {
    Optional<UserStateEntity> findUserStateEntityByUserState(UserState userState);
}
