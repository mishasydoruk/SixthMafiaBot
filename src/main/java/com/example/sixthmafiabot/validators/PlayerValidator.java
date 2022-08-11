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

    public void validateCreatePlayerDTO(CreatePlayerDTO player)
            throws ValidationException {

        validateDTO(player);
    }

    public void validateUpdatePlayerDTO(UpdatePlayerDTO player)
            throws ValidationException {

        validateDTO(player);
    }

    public void validateIfAlreadyExists(CreatePlayerDTO player) throws AlreadyExistsException {

        boolean alreadyExists = playerRepository
                .getPlayerByUserTelegramId(player.getTelegramId()) != null;

        if(alreadyExists){
            throw new AlreadyExistsException("telegramId",
                    "Player with user identifier = %s already created"
                    .formatted(player.getTelegramId())
            );
        }
    }

    public CreatePlayerDTO validateCreate(CreatePlayerDTO player) throws ServiceValidationError {

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

        return player;
    }

    public UpdatePlayerDTO validateUpdate(UpdatePlayerDTO player) throws ServiceValidationError {

        try {
            validateUpdatePlayerDTO(player);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getExceptionMap());
        }

        return player;

    }

}
