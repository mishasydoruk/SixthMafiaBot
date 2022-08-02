package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.exceptions.AlreadyExistsExcepeion;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.models.User;
import com.example.sixthmafiabot.repository.UserRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
public class UserValidator extends BaseValidator {

    @Autowired
    UserRepository userRepository;

    @Async("asyncExecutor")
    public void validateUser(User user) throws ValidationException{
        Set<ConstraintViolation<User>> violations =
                validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<User> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ValidationException("Error occurred: " + sb.toString());
        }
    }

    @Async("asyncExecutor")
    public void validateIfAlreadyExists(User user) throws AlreadyExistsExcepeion {

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
    public CompletableFuture<User> validateCreate(User user) throws ServiceValidationError {

        try{
            validateIfAlreadyExists(user);
        }
        catch (AlreadyExistsExcepeion ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        try {
            validateUser(user);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        return CompletableFuture.completedFuture(user);
    }

    @Async("asyncExecutor")
    public CompletableFuture<User> validateUpdate(User user) throws ServiceValidationError {

        try {
            validateUser(user);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        return CompletableFuture.completedFuture(user);
    }

}
