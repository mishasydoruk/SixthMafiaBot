package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.DTO.CreateEnvironmentDTO;
import com.example.sixthmafiabot.exceptions.AlreadyExistsException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;

import com.example.sixthmafiabot.repository.EnvironmentRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EnvironmentValidator extends BaseValidator {

    @Autowired
    EnvironmentRepository environmentRepository;

    public void validateAlreadyExists(CreateEnvironmentDTO env) throws AlreadyExistsException {

        boolean alreadyExists = environmentRepository
                .getEnvironmentByChatId(env.getChatId()) != null;

        if(alreadyExists){
            throw new AlreadyExistsException("chatId",
                    "Environment with chatId = %s already existed!"
                            .formatted(env.getChatId())
            );
        }
    }

    public CreateEnvironmentDTO validateCreate(CreateEnvironmentDTO env) throws ServiceValidationError {

        validateDTO(env);

       try {
           validateAlreadyExists(env);
       }
       catch (AlreadyExistsException ex){
           throw new ServiceValidationError(ex.getField(), ex.getErrorMessage());
       }

       return env;
    }
}
