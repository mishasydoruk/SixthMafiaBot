package com.example.sixthmafiabot.exceptions.Abstract;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class CustomMapException extends BaseCustomException{

    protected final Map <String, String> exceptionMap;

    public CustomMapException(Map<String, String> exceptionMap) {

        super(exceptionMap.toString());

        this.exceptionMap = exceptionMap;
    }
}
