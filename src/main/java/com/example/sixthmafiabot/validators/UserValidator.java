package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.DTO.CreateUserDTO;
import com.example.sixthmafiabot.DTO.UpdateUserDTO;
import com.example.sixthmafiabot.exceptions.AlreadyExistsException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.exceptions.ValidationException;
import com.example.sixthmafiabot.repository.UserRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class UserValidator extends BaseValidator {

    @Autowired
    UserRepository userRepository;

    @Async("validatorExecutor")
    public void validateCreateUser(CreateUserDTO user) throws ValidationException {

        validateDTO(user);
    }

    @Async("validatorExecutor")
    public void validateUpdateUser(UpdateUserDTO user) throws ValidationException {

        validateDTO(user);
    }

    @Async("validatorExecutor")
    public void validateIfAlreadyExists(CreateUserDTO user) throws AlreadyExistsException {

        boolean alreadyExists =  userRepository
                .getUserByTelegramId(user.getTelegramId())
                .join() != null;

        if(alreadyExists){

            throw new AlreadyExistsException("telegramId",
                    "User with identifier = %s already exists"
                    .formatted(user.getTelegramId())
            );
        }
    }

    @Async("validatorExecutor")
    public CompletableFuture<CreateUserDTO> validateCreate(CreateUserDTO createUserDTO) throws ServiceValidationError {

        try{
            validateIfAlreadyExists(createUserDTO);
        }
        catch (AlreadyExistsException ex){
            throw new ServiceValidationError(ex.getField(), ex.getErrorMessage());

        }


        try {
            validateCreateUser(createUserDTO);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getExceptionMap());
        }

        return CompletableFuture.completedFuture(createUserDTO);
    }


    @Async("validatorExecutor")
    public CompletableFuture<UpdateUserDTO> validateUpdate(UpdateUserDTO updateUserDTO) throws ServiceValidationError {

        try {
            validateUpdateUser(updateUserDTO);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getExceptionMap());
        }

        return CompletableFuture.completedFuture(updateUserDTO);
    }

}
