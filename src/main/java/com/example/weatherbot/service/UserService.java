package com.example.weatherbot.service;

import com.example.weatherbot.enums.UserState;
import com.example.weatherbot.exception.UserNotFoundException;
import com.example.weatherbot.factory.UserFactory;
import com.example.weatherbot.model.User;
import com.example.weatherbot.model.UserStateEntity;
import com.example.weatherbot.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final StateService stateService;
    public UserService(UserRepository userRepository, StateService stateService) {
        this.userRepository = userRepository;
        this.stateService = stateService;
    }
    public void createUserIfNotExists(Long chatId, UserState state) {
        if (userRepository.existsUserByChatId(chatId)){
            log.info("User {} already exists, skip creating", chatId);
            return;
        }

        UserStateEntity stateEntity = stateService.getUserStateEntityOrCreate(state);
        User user = UserFactory.getUserWithInBuiltSchedule(chatId, stateEntity);
        userRepository.save(user);
        log.info("Saved user {}", chatId);
    }
    /**
     * Изменяет состояние пользователя.
     * @param chatId Id пользователя, которому нужно сменить состояние
     * @param userState Состояние, которое будет присвоено пользователю
     */
    @SneakyThrows
    public void updateUserState(Long chatId, UserState userState){
        User user = userRepository.findById(chatId).orElseThrow(() ->
                new UserNotFoundException(String.format("Cannot update user state of %s because was not found", chatId)));

        UserStateEntity userStateEntity = stateService.getUserStateEntityOrCreate(userState);
        user.setUserStateEntity(userStateEntity);
        userRepository.save(user);
        log.info("Updated state of {}", user);
    }
    @SneakyThrows
    public void updateUserCity(Long chatId, String city){
        User user = userRepository.findById(chatId).orElseThrow(() ->
                new UserNotFoundException(String.format("Cannot update user state of %s because was not found", chatId)));

        user.setCity(city);
        userRepository.save(user);
        log.info("Updated city of {}", user);
    }
    public void updateUser(User user){
        if(userRepository.existsById(user.getChatId())){
            userRepository.save(user);
            log.info("Updated user {}", user.getChatId());
        } else {
            log.info("User {} not found and can not be updated", user.getChatId());
        }
    }
    public Optional<User> findUserByChatId(Long chatId){
        return userRepository.findByChatId(chatId);
    }
}
