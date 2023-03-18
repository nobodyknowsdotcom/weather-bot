package com.example.weatherbot.service;

import com.example.weatherbot.model.UserState;
import com.example.weatherbot.model.User;
import com.example.weatherbot.model.UserStateEntity;
import com.example.weatherbot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUserIfNotExists(Long chatId, UserState state) {

        if (userRepository.existsById(chatId)){
            return;
        }

        UserStateEntity userStateEntity = UserStateEntity.builder()
                .userState(state)
                .build();
        User userToSave = User.builder()
                .chatId(chatId)
                .build();
        userStateEntity.setUser(userToSave);
        userToSave.setUserStateEntity(userStateEntity);

        userRepository.save(userToSave);
    }

    public Optional<User> findUserByChatId(Long chatId){
        return userRepository.findByChatId(chatId);
    }
}
