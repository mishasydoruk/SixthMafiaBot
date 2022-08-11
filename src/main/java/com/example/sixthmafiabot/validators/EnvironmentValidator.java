package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.DTO.CreateEnvironmentDTO;
import com.example.sixthmafiabot.exceptions.AlreadyExistsException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.exceptions.ValidationException;
import com.example.sixthmafiabot.models.Environment;

import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringEnvironmentRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


@Component
public class EnvironmentValidator extends BaseValidator {

    @Autowired
    SpringEnvironmentRepository environmentRepository;

    public void validateIfAlreadyExists(CreateEnvironmentDTO env) throws AlreadyExistsException {

        boolean alreadyExisted = environmentRepository
                .getEnvironmentByChatId(env.getChatId()) != null;

        if(alreadyExisted){
            throw new AlreadyExistsException("chatId",
                    "Environment with chatId = %s already existed!"
                            .formatted(env.getChatId())
            );
        }

    }

    public void validateEnvironment(CreateEnvironmentDTO env)
            throws ValidationException {

        validateDTO(env);
    }

    public CreateEnvironmentDTO validateCreate(CreateEnvironmentDTO env) throws ServiceValidationError {

        try {
            validateEnvironment(env);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getExceptionMap());
        }

       try {
           validateIfAlreadyExists(env);
       }
       catch (AlreadyExistsException ex){
           throw new ServiceValidationError(ex.getField(), ex.getErrorMessage());
       }

       return env;
    }
}
