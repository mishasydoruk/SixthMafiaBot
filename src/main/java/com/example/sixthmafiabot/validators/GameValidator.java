package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.DTO.CreateGameDTO;
import com.example.sixthmafiabot.DTO.UpdateGameDTO;
import com.example.sixthmafiabot.exceptions.AlreadyExistsException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.exceptions.ValidationException;
import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringGameRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


import java.util.concurrent.CompletableFuture;

@Component
public class GameValidator extends BaseValidator {

    @Autowired
    private SpringGameRepository gameRepository;


    @Async("validatorExecutor")
    public void validateIfAlreadyExists(CreateGameDTO game)
            throws AlreadyExistsException {


        boolean gameAlreadyExists = gameRepository
                .findGameByEnvironmentChatId(game.getEnvironmentId())
                .join() != null;

        if(gameAlreadyExists){

            throw new AlreadyExistsException(
                    "environmentId",
                    "Game already created in environment with id="
                            + game.getEnvironmentId()
            );
        }

    }

    @Async("validatorExecutor")
    private void validateCreateGame(CreateGameDTO game) throws ValidationException {

        validateDTO(game);
    }

    @Async("validatorExecutor")
    private void validateUpdateGame(UpdateGameDTO game) throws ValidationException {

        validateDTO(game);
    }

    @Async("validatorExecutor")
    public CompletableFuture<CreateGameDTO> validateCreate(CreateGameDTO game) throws ServiceValidationError {

        try{
            validateIfAlreadyExists(game);
        }
        catch (AlreadyExistsException ex){
            throw new ServiceValidationError(ex.getField(), ex.getErrorMessage());
        }


        try {
            validateCreateGame(game);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getExceptionMap());
        }


        return CompletableFuture.completedFuture(game);
    }

    @Async("validatorExecutor")
    public CompletableFuture<UpdateGameDTO> validateUpdate(UpdateGameDTO newGame) throws ServiceValidationError {

        try {
            validateUpdateGame(newGame);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getExceptionMap());
        }

        return CompletableFuture.completedFuture(newGame);

    }

}
