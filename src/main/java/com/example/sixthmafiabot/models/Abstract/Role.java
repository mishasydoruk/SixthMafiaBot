package com.example.sixthmafiabot.models.Abstract;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class Role extends BaseModel {

    @Column(name = "name")
    protected String name;

}
