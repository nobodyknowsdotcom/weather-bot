package com.example.weatherbot.model;

import com.example.weatherbot.enums.UserState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "weather_user_state")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserStateEntity {
    @Id
    @GeneratedValue
    @Column(name = "state_id")
    Long id;
    @Enumerated(value = EnumType.STRING)
    UserState userState;
    public UserStateEntity(UserState userState){
        this.userState = userState;
    }
}
