package com.example.weatherbot.model;

import com.example.weatherbot.enums.UserState;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "weather_users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    @Enumerated(value = EnumType.STRING)
    UserState userState;
    @Builder.Default
    Integer apiCalls = 0;

    public void incrementApiCalls() {
        this.apiCalls++;
    }
}
