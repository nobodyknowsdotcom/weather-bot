package com.example.weatherbot.service;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.model.ForecastSchedule;
import com.example.weatherbot.model.User;
import com.example.weatherbot.repository.ForecastScheduleRepository;
import com.example.weatherbot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ForecastScheduleRepository forecastScheduleRepository;

    public UserService(UserRepository userRepository, ForecastScheduleRepository forecastScheduleRepository) {
        this.userRepository = userRepository;
        this.forecastScheduleRepository = forecastScheduleRepository;
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
         ForecastSchedule forecastSchedule;

        user.setUserState(userState);

        if(user.getForecastSchedule() == null){
             forecastSchedule = new ForecastSchedule();
             forecastSchedule.setUser(user);
             user.setForecastSchedule(forecastSchedule);
             forecastScheduleRepository.save(forecastSchedule);
         }


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

    public boolean updateUser(User user){
        if(userRepository.existsById(user.getChatId())){
            userRepository.save(user);
            log.info("updated user {}", user.getChatId());
            return true;
        } else {
            log.info("user {} not found and can not be updated", user.getChatId());
            return false;
        }

    }

    public Optional<User> findUserByChatId(Long chatId){
        return userRepository.findByChatId(chatId);
    }
}
