package com.example.weatherbot.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "forecast_schedules")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForecastSchedule {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long scheduleId;
    @Builder.Default
    Boolean isScheduleEnabled = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Override
    public String toString() {
        return "ForecastSchedule{" +
                "scheduleId=" + scheduleId +
                ", isScheduleEnabled=" + isScheduleEnabled +
                "User{" +
                "chatId=" + user.getChatId() +
                ", city='" + user.getCity() + '\'' +
                ", forecastSchedule=" + "this" +
                ", userState=" + user.getUserStateEntity() +
                ", apiCalls=" + user.getApiCalls() +
                '}' +
                '}';
    }
}
