package com.example.sixthmafiabot.models;

import com.example.sixthmafiabot.models.Abstract.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseModel {

    @Column(name = "username")
    String username;

    @Column(name = "telegram_id")
    Long telegramId;

}
