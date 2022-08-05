package com.example.sixthmafiabot.models;

import com.example.sixthmafiabot.models.Abstract.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseModel {

    @NotNull @NotBlank
    @Column(name = "username")
    String username;

    @NotNull
    @Column(name = "telegram_id")
    Long telegramId;

}
