package com.example.weatherbot.model;

import com.example.weatherbot.enums.UserState;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "weather_users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "user_id")
    Long chatId;
    String city;
    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    ForecastSchedule forecastSchedule;
    @OneToOne
    @JoinTable(
            name = "users_states",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "state_id"))
    UserStateEntity userStateEntity;
    @Builder.Default
    Integer apiCalls = 0;

    public void incrementApiCalls() {
        this.apiCalls++;
    }
    public UserState getUserState(){
        return this.userStateEntity.getUserState();
    }
}
