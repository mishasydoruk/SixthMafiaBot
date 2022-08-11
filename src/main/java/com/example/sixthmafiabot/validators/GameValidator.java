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

    public void alreadyExists(CreateGameDTO game)
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

    private void validateCreateGame(CreateGameDTO game) throws ServiceValidationError {

        validateDTO(game);
    }

    private void validateUpdateGame(UpdateGameDTO game) throws ServiceValidationError {

        validateDTO(game);
    }

    public CreateGameDTO validateCreate(CreateGameDTO game) throws ServiceValidationError {

        try{
            alreadyExists(game);
        }
        catch (AlreadyExistsException ex){
            throw new ServiceValidationError(ex.getField(), ex.getErrorMessage());
        }

        validateCreateGame(game);

        return game;
    }

    public UpdateGameDTO validateUpdate(UpdateGameDTO newGame) throws ServiceValidationError {

        validateUpdateGame(newGame);

        return newGame;
    }

}
