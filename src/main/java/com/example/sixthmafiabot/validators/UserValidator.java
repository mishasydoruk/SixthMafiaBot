package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.DTO.UserDTO;
import com.example.sixthmafiabot.exceptions.AlreadyExistsExcepeion;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.User;
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
    public void validateUser(UserDTO user) throws ValidationException{
        Set<ConstraintViolation<UserDTO>> violations =
                validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UserDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ValidationException("Error occurred: " + sb.toString());
        }
    }

    @Async("asyncExecutor")
    public void validateIfAlreadyExists(UserDTO user) throws AlreadyExistsExcepeion {

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
    public CompletableFuture<UserDTO> validateCreate(UserDTO userDTO) throws ServiceValidationError {

        try{
            validateIfAlreadyExists(userDTO);
        }
        catch (AlreadyExistsExcepeion ex){
            throw new ServiceValidationError(ex.getMessage());

        }

        try {
            validateUser(userDTO);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        return CompletableFuture.completedFuture(userDTO);
    }

    @Async("asyncExecutor")
    public CompletableFuture<UserDTO> validateUpdate(UserDTO userDTO) throws ServiceValidationError {

        try {
            validateUser(userDTO);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        return CompletableFuture.completedFuture(userDTO);
    }

}
