package com.example.sixthmafiabot.models.Abstract;

public  abstract class Role extends BaseModel {

    enum Side{
        MAFIA,
        PEACE
    }

    private String name;

    private Side side;
}
