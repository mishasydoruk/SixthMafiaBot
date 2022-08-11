package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.DTO.CreateGameDTO;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.repository.GameRepository;
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
    GameRepository gameRepository;

    @Autowired
    GameValidator gameValidator;

    @Async("serviceExecutor")
    public CompletableFuture<Game> createGame(CreateGameDTO createGameDTO) throws ServiceValidationError {

        CreateGameDTO validatedGame = gameValidator.validateCreate(createGameDTO);

        Game game = gameRepository.create(validatedGame);

        return CompletableFuture.completedFuture(game);
    }


    @Async("serviceExecutor")
    public CompletableFuture<Game> getGame(Long envId)  {

        Game game = gameRepository.getGameByEnvironmentChatId(envId);

        return CompletableFuture.completedFuture(game);
    }


}
