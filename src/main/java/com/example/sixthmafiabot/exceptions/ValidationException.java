package com.example.sixthmafiabot.exceptions;

import com.example.sixthmafiabot.exceptions.Abstract.BaseCustomException;
import com.example.sixthmafiabot.exceptions.Abstract.CustomMapException;

import java.util.Map;

public class ValidationException extends CustomMapException {

    public ValidationException(Map<String, String> exceptionMap) {

        super(exceptionMap);
    }
}
