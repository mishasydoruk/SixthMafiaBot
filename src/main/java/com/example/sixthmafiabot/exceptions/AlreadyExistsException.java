package com.example.sixthmafiabot.exceptions;

import com.example.sixthmafiabot.exceptions.Abstract.CustomOneFieldException;

public class AlreadyExistsException extends CustomOneFieldException {


    public AlreadyExistsException(String field, String message) {
        super(field, message);
    }
}
