package com.example.sixthmafiabot.models;

import com.example.sixthmafiabot.models.Abstract.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Player extends BaseModel {

    @OneToOne
    private User user;

    @ManyToOne
    private Game game;

    @Column(name = "is_alive")
    private Boolean isAlive = true;

}
