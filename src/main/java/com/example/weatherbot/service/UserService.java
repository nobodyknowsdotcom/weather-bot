package com.example.weatherbot.service;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.model.User;
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

        if (userRepository.existsUserByChatId(chatId)){
            return;
        }

        User userToSave = User.builder()
                .chatId(chatId)
                .userState(state)
                .build();

        log.info("saving user {}", chatId);
        userRepository.save(userToSave);
    }

    public void updateUserState(Long chatId, UserState userState){

         if(!userRepository.existsById(chatId)){
             return;
         }

         User user = userRepository.findById(chatId).get();
         user.setUserState(userState);

         log.info("updating user {}", user);
         userRepository.save(user);
    }

    public Optional<User> findUserByChatId(Long chatId){
        return userRepository.findByChatId(chatId);
    }
}
