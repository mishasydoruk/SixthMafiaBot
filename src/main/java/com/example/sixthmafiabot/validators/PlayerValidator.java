package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.DTO.CreatePlayerDTO;
import com.example.sixthmafiabot.DTO.UpdatePlayerDTO;
import com.example.sixthmafiabot.exceptions.AlreadyExistsException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.exceptions.ValidationException;
import com.example.sixthmafiabot.repository.PlayerRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class PlayerValidator extends BaseValidator {

    @Autowired
    PlayerRepository playerRepository;

    @Async("validatorExecutor")
    public void validateCreatePlayerDTO(CreatePlayerDTO player)
            throws ValidationException {

        validateDTO(player);
    }

    @Async("validatorExecutor")
    public void validateUpdatePlayerDTO(UpdatePlayerDTO player)
            throws ValidationException {

        validateDTO(player);
    }

    @Async("validatorExecutor")
    public void validateIfAlreadyExists(CreatePlayerDTO player) throws AlreadyExistsException {

        boolean alreadyExists = playerRepository
                .getPlayerByUserTelegramId(
                        player.getTelegramId()
                )
                .join() != null;

        if(alreadyExists){

            throw new AlreadyExistsException("telegramId",
                    "Player with user identifier = %s already created"
                    .formatted(player.getTelegramId())
            );
        }
    }

    @Async("validatorExecutor")
    public CompletableFuture<CreatePlayerDTO> validateCreate(CreatePlayerDTO player) throws ServiceValidationError {

        try{
            validateIfAlreadyExists(player);
        }

        catch (AlreadyExistsException ex){
            throw new ServiceValidationError(ex.getField(), ex.getErrorMessage());
        }

        try {
            validateCreatePlayerDTO(player);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getExceptionMap());
        }

        return CompletableFuture.completedFuture(player);
    }

    @Async("validatorExecutor")
    public CompletableFuture<UpdatePlayerDTO> validateUpdate(UpdatePlayerDTO player) throws ServiceValidationError {

        try {
            validateUpdatePlayerDTO(player);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getExceptionMap());
        }

        return CompletableFuture.completedFuture(player);

    }

}
