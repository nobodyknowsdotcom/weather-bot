package com.example.weatherbot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "weatherfy_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    Long chatId;
    String city;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    UserStateEntity userStateEntity;
    Integer apiCalls;

    public UserState getUserState(){
        return this.userStateEntity.getUserState();
    }
}
