package com.example.weatherbot.service;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.model.UserStateEntity;
import com.example.weatherbot.repository.StateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }
    @Transactional
    public UserStateEntity getUserStateEntityOrCreate(UserState userState){
        return stateRepository.findUserStateEntityByUserState(userState).orElseGet(
                () -> stateRepository.save(new UserStateEntity(userState))
        );
    }
}
