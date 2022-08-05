package com.example.sixthmafiabot.validators.Abstract;


import javax.validation.Validation;
import javax.validation.Validator;

public abstract class BaseValidator {

    protected final Validator validator = Validation
            .buildDefaultValidatorFactory()
            .getValidator();

}
