package com.example.sixthmafiabot.exceptions;

import com.example.sixthmafiabot.exceptions.Abstract.BaseCustomException;

public class NotFoundException extends BaseCustomException {

    public NotFoundException(String msg) {
        super(msg);
    }
}
