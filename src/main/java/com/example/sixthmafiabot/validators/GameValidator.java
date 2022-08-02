package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.exceptions.AlreadyExistsExcepeion;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.repository.GameRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component
public class GameValidator extends BaseValidator {

    @Autowired
    private GameRepository gameRepository;


    public void validateIfAlreadyExists(Environment env) throws AlreadyExistsExcepeion {

        boolean gameAlreadyExists = gameRepository.getGameByEnvironment(env).join()!=null;

        if(gameAlreadyExists){
            throw new AlreadyExistsExcepeion("Game already created in environment with id="+env.getChatId());
        }
    }


    private void validateGame(Game game) throws ValidationException{

        Set<ConstraintViolation<Game>> violations =
                validator.validate(game);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Game> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new  ValidationException("Error occurred: " + sb.toString());
        }
    }


    @Async("asyncExecutor")
    public CompletableFuture<Game> validateCreate(Environment env) throws ServiceValidationError {

        try{
            validateIfAlreadyExists(env);
        }
        catch (AlreadyExistsExcepeion ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        Game gameToCreate = new Game(env);

        try {
            validateGame(gameToCreate);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getMessage());
        }


        return CompletableFuture.completedFuture(gameToCreate);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Game> validateUpdate(Game newGame) throws ServiceValidationError {

        try {
            validateGame(newGame);
        }
        catch (ValidationException ex){
            throw new ServiceValidationError(ex.getMessage());
        }

        return CompletableFuture.completedFuture(newGame);

    }




}
