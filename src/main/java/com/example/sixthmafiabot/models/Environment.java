package com.example.sixthmafiabot.models;


import com.example.sixthmafiabot.models.Abstract.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="environments")
@Getter
@Setter
public class Environment extends BaseModel {

    @Column(name = "chat_id")
    private Long chatId;

    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

}
