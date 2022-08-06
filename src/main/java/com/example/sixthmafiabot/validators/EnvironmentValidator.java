package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.exceptions.AlreadyExistsExcepeion;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.exceptions.ValidateException;
import com.example.sixthmafiabot.models.Environment;

import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringEnvironmentRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;


@Component
public class EnvironmentValidator extends BaseValidator {

    @Autowired
    SpringEnvironmentRepository environmentRepository;

    public void validateIfAlreadyExists(Environment env) throws AlreadyExistsExcepeion {

        boolean alreadyExisted = environmentRepository
                .getEnvironmentByChatId(env.getChatId()) != null;

        if(alreadyExisted){
            throw new AlreadyExistsExcepeion("Environment with chatId = %s already existed!".formatted(env.getChatId()));
        }

    }

    public void validateEnvironment(Environment env) throws ValidateException {

        validateDTO(env);
    }

    public CompletableFuture<Environment> validateCreate(Environment env) throws ServiceValidationError {

        try {
            validateEnvironment(env);
        }
        catch (ValidateException ex){
            throw new ServiceValidationError(ex.getMessage());
        }


       try {
           validateIfAlreadyExists(env);
       }
       catch (AlreadyExistsExcepeion ex){
           throw new ServiceValidationError(ex.getMessage());
       }

       return CompletableFuture.completedFuture(env);
    }
}
