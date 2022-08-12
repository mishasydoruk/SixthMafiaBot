package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.DTO.CreatePlayerDTO;
import com.example.sixthmafiabot.DTO.UpdatePlayerDTO;
import com.example.sixthmafiabot.exceptions.AlreadyExistsException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.repository.PlayerRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerValidator extends BaseValidator {

    @Autowired
    PlayerRepository playerRepository;

    public void validateAlreadyExists(CreatePlayerDTO player) throws AlreadyExistsException {

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
            validateAlreadyExists(player);
        }

        catch (AlreadyExistsException ex){
            throw new ServiceValidationError(ex.getField(), ex.getErrorMessage());
        }

        validateDTO(player);

        return player;
    }

    public UpdatePlayerDTO validateUpdate(UpdatePlayerDTO player) throws ServiceValidationError {

        validateDTO(player);

        return player;
    }

}
