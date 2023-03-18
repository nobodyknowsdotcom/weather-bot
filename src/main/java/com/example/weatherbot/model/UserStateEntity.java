package com.example.weatherbot.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "weatherfy_user_state")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserStateEntity {
    @Id
    @Column(name = "user_id")
    Long id;
    @Enumerated(EnumType.STRING)
    UserState userState;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    User user;
}
