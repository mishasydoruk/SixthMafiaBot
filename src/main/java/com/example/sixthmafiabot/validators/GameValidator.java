package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.DTO.CreateGameDTO;
import com.example.sixthmafiabot.DTO.UpdateGameDTO;
import com.example.sixthmafiabot.exceptions.AlreadyExistsException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.repository.GameRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameValidator extends BaseValidator {

    @Autowired
    private GameRepository gameRepository;

    public void validateAlreadyExists(CreateGameDTO game)
            throws AlreadyExistsException {


        boolean gameAlreadyExists = gameRepository
                .getGameByEnvironmentChatId(game.getEnvironmentId()) != null;

        if(gameAlreadyExists){

            throw new AlreadyExistsException(
                    "environmentId",
                    "Game already created in environment with id="
                            + game.getEnvironmentId()
            );
        }
    }

    public CreateGameDTO validateCreate(CreateGameDTO game) throws ServiceValidationError {

        try{
            validateAlreadyExists(game);
        }
        catch (AlreadyExistsException ex){
            throw new ServiceValidationError(ex.getField(), ex.getErrorMessage());
        }

        validateDTO(game);

        return game;
    }

    public UpdateGameDTO validateUpdate(UpdateGameDTO newGame) throws ServiceValidationError {

        validateDTO(newGame);

        return newGame;
    }

}
