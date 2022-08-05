package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.exceptions.AlreadyExistsExcepeion;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.Player;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringPlayerRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
public class PlayerValidator extends BaseValidator {

    @Autowired
    SpringPlayerRepository playerRepository;

    @Async("asyncExecutor")
    public void validatePlayer(Player player) throws ValidationException{

        Set<ConstraintViolation<Player>> violations =
                validator.validate(player);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Player> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ValidationException("Error occurred: " + sb.toString());
        }
    }

    @Async("asyncExecutor")
    public void validateIfAlreadyExists(Player player) throws AlreadyExistsExcepeion {

        boolean alreadyExists = playerRepository.getPlayerByUser(player.getUser()).join()!=null;

        if(alreadyExists){
            throw new AlreadyExistsExcepeion("Player with user identifier = %s already created"
                    .formatted(player.getUser().getTelegramId())
            );
        }

    }

    @Async("asyncExecutor")
    public CompletableFuture<Player> validateCreate(Player player) throws ServiceValidationError {

        try{
            validateIfAlreadyExists(player);
        }
        catch (AlreadyExistsExcepeion ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        try {
            validatePlayer(player);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        return CompletableFuture.completedFuture(player);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Player> validateUpdate(Player player) throws ServiceValidationError {

        try {
            validatePlayer(player);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        return CompletableFuture.completedFuture(player);

    }

}
