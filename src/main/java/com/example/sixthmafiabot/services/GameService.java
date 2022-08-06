package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.exceptions.NotFoundException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringGameRepository;
import com.example.sixthmafiabot.services.Abstract.BaseService;
import com.example.sixthmafiabot.validators.GameValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GameService implements BaseService {

    @Autowired
    SpringGameRepository springGameRepository;

    @Autowired
    GameValidator gameValidator;

    @Async("asyncExecutor")
    public CompletableFuture<Boolean> createGame(Environment env) throws ServiceValidationError {

        Game game = gameValidator.validateCreate(env).join();
        springGameRepository.save(game);
        return CompletableFuture.completedFuture(true);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Integer> getRegistrationTime(Environment env){

        return CompletableFuture.completedFuture(10);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Game> getGame(Environment env) throws NotFoundException {

        Game game = springGameRepository.getGameByEnvironment(env).join();

        if (game == null){

            throw new NotFoundException("No game in chat with id = "+env.getChatId());
        }

        return CompletableFuture.completedFuture(game);
    }


}
