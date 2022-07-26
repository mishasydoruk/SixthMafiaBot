package com.example.sixthmafiabot.models;

import com.example.sixthmafiabot.enums.GameStatus;
import com.example.sixthmafiabot.models.Abstract.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
@Getter
@Setter
public class Game extends BaseModel {

    @OneToMany(mappedBy = "game")
    private Set<Player> players = new HashSet<>();

    @OneToOne(mappedBy = "game")
    private final Environment environment;

    @NotNull @Column(name="game_status")
    private GameStatus gameStatus = GameStatus.REGISTRATION;

    @NotNull @Column(name = "registration_start_time")
    private final LocalDateTime registrationStartTime;

    public Game(Environment environment, LocalDateTime registrationStartTime){

        this.environment = environment;
        this.registrationStartTime = registrationStartTime;
    }

    protected Game() {
        this.environment = null;
        this.registrationStartTime = null;
    }
}
