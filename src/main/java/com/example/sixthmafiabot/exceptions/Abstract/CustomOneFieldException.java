package com.example.sixthmafiabot.exceptions.Abstract;

import lombok.Getter;

@Getter
public  abstract class CustomOneFieldException extends BaseCustomException{

    protected final String field;
    protected final String errorMessage;

    public CustomOneFieldException(String field, String errorMessage) {

        this.field = field;
        this.errorMessage = errorMessage;
    }
}
