package com.example.sixthmafiabot.models;


import com.example.sixthmafiabot.models.Abstract.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="environments")
@Getter
@Setter
public class Environment extends BaseModel {

    @Column(name = "telegram_id")
    private Long telegram_id;




}
