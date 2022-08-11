package com.example.sixthmafiabot.models.Abstract;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel {

    @Id
    @Column(name = "id")
    @GeneratedValue
    protected Long id;

}
