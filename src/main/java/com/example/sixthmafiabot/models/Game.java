package com.example.sixthmafiabot.models;

import com.example.sixthmafiabot.models.Abstract.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
@Getter
@Setter
public class Game extends BaseModel {

    @OneToMany(mappedBy = "game")
    private Set<Player> players = new HashSet<>();

    @NotNull @Column(name = "chat_id", unique = true)
    private Long chatId;

    @NotNull @Column(name="started")
    private Boolean started = false;

    public Game(Long chatId){
        this.chatId = chatId;
    }

    protected Game() {

    }
}
