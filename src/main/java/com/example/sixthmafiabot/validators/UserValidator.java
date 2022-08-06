package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.DTO.CreateUserDTO;
import com.example.sixthmafiabot.DTO.UpdateUserDTO;
import com.example.sixthmafiabot.exceptions.AlreadyExistsExcepeion;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.exceptions.ValidateException;
import com.example.sixthmafiabot.repository.UserRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class UserValidator extends BaseValidator {

    @Autowired
    UserRepository userRepository;

    @Async("asyncExecutor")
    public void validateCreateUser(CreateUserDTO user) throws ValidateException{

        validateDTO(user);
    }

    public void validateUpdateUser(UpdateUserDTO user) throws ValidateException{

        validateDTO(user);
    }

    @Async("asyncExecutor")
    public void validateIfAlreadyExists(CreateUserDTO user) throws AlreadyExistsExcepeion {

        boolean alreadyExists =  userRepository
                .getUserByTelegramId(user.getTelegramId())
                .join() != null;

        if(alreadyExists){

            throw new AlreadyExistsExcepeion("User with identifier = %s already exists"
                    .formatted(user.getTelegramId())
            );
        }
    }

    @Async("asyncExecutor")
    public CompletableFuture<CreateUserDTO> validateCreate(CreateUserDTO createUserDTO) throws ServiceValidationError {

        try{
            validateIfAlreadyExists(createUserDTO);
        }
        catch (AlreadyExistsExcepeion ex){
            throw new ServiceValidationError(ex.getMessage());

        }

        try {
            validateCreateUser(createUserDTO);
        }
        catch (ValidateException ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        return CompletableFuture.completedFuture(createUserDTO);
    }

    @Async("asyncExecutor")
    public CompletableFuture<UpdateUserDTO> validateUpdate(UpdateUserDTO updateUserDTO) throws ServiceValidationError {

        try {
            validateUpdateUser(updateUserDTO);
        }
        catch (ValidateException ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        return CompletableFuture.completedFuture(updateUserDTO);
    }

}
