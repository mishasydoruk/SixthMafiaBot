package com.example.sixthmafiabot.validators.Abstract;

import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseValidator {

    protected final Validator validator = Validation
            .buildDefaultValidatorFactory()
            .getValidator();



    protected <T> void validateDTO(T modelDTO) throws ServiceValidationError {

        Set<ConstraintViolation<T>> violations =
                validator.validate(modelDTO);

        if (!violations.isEmpty()) {

            Map<String, String> exceptionMap = new HashMap<>();

            for (ConstraintViolation<T> next : violations) {

                exceptionMap.put(
                        ((PathImpl) next.getPropertyPath())
                                .getLeafNode()
                                .getName(),
                        next.getMessage()
                );

                throw new ServiceValidationError(exceptionMap);
            }
        }

    }
}
