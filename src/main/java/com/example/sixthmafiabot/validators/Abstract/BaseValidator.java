package com.example.sixthmafiabot.validators.Abstract;

import com.example.sixthmafiabot.models.Abstract.BaseModel;

import javax.validation.Validation;
import javax.validation.Validator;

public abstract class BaseValidator {

    protected final Validator validator = Validation
            .buildDefaultValidatorFactory()
            .getValidator();

}
