package com.example.sixthmafiabot.exceptions;

import com.example.sixthmafiabot.exceptions.Abstract.BaseCustomException;
import com.example.sixthmafiabot.exceptions.Abstract.CustomMapException;
import com.example.sixthmafiabot.exceptions.Abstract.CustomOneFieldException;

import java.util.HashMap;
import java.util.Map;

public class ServiceValidationError extends CustomMapException {


    public ServiceValidationError(String field, String message) {
        super(Map.of(field, message));
    }

    public ServiceValidationError(Map<String, String> exceptionMap){
        super(exceptionMap);
    }

}
