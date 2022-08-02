package com.example.sixthmafiabot.exceptions;

import com.example.sixthmafiabot.exceptions.Abstract.BaseCustomException;

public class ServiceValidationError extends BaseCustomException {

    public ServiceValidationError(String msg) {
        super(msg);
    }
}
