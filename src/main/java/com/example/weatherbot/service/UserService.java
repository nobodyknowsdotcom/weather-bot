package com.example.weatherbot.service;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.model.User;
import com.example.weatherbot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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
            log.info("user {} already exists, skip creating", chatId);
            return;
        }

        User userToSave = User.builder()
                .chatId(chatId)
                .userState(state)
                .apiCalls(0)
                .build();

        log.info("saving user {}", chatId);
        userRepository.save(userToSave);
    }

    @Async
    public void updateUserState(Long chatId, UserState userState){

         Optional<User> optionalUser = userRepository.findById(chatId);
         if(optionalUser.isEmpty()){
            log.error("cannot update user state of {} because was not found", chatId);
            return;
         }
         User user = optionalUser.get();
         user.setUserState(userState);

         userRepository.save(user);
        log.info("updated state of {}", user);
    }

    @Async
    public void updateUserCity(Long chatId, String city){

        Optional<User> optionalUser = userRepository.findById(chatId);
        if(optionalUser.isEmpty()){
            log.error("cannot update city of {} because was not found", chatId);
            return;
        }
        User user = optionalUser.get();
        user.setCity(city);

        userRepository.save(user);
        log.info("updated city of {}", user);
    }

    public Optional<User> findUserByChatId(Long chatId){
        return userRepository.findByChatId(chatId);
    }
}
