package com.example.sixthmafiabot.exceptions;

import com.example.sixthmafiabot.exceptions.Abstract.BaseCustomException;

import java.util.Map;

public class ValidateException extends BaseCustomException {

    public ValidateException(Map<String, String> exceptionMap) {

        super(exceptionMap.toString());
    }
}
