package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.repository.GameRepository;
import com.example.sixthmafiabot.services.Abstract.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class GameService implements BaseService {

    @Autowired
    GameRepository gameRepository;

    @Async("asyncExecutor")
    public CompletableFuture<Boolean> createGame(Long chatId)  {

        if(gameRepository.getGameByChatId(chatId) == null){
            Game game = new Game(chatId);
            gameRepository.save(game);
            return CompletableFuture.completedFuture(true);
        }

        return CompletableFuture.completedFuture(false);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Integer> getRegistrationTime(Long chatId){

        return CompletableFuture.completedFuture(10);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Boolean> gameIsStarted(Long chatId){
        Game game = gameRepository.getGameByChatId(chatId);

        if(game!=null){
            return CompletableFuture.completedFuture(game.getStarted());
        }

        return CompletableFuture.completedFuture(true);

    }


    @Async("asyncExecutor")
    public CompletableFuture<Boolean> startGameByChatId(Long chatId){

        Game game = gameRepository.getGameByChatId(chatId);

        if(game == null){
            return CompletableFuture.completedFuture(false);
        }

        if(game.getStarted()){
            return CompletableFuture.completedFuture(false);
        }

        game.setStarted(true);
        gameRepository.save(game);

        return CompletableFuture.completedFuture(true);


    }

    public CompletableFuture<Boolean> cancelRegistration(Long chatId){

        Game game = gameRepository.getGameByChatId(chatId);

        if(game == null){
            return CompletableFuture.completedFuture(false);
        }

        if(game.getStarted()){
            return CompletableFuture.completedFuture(false);
        }

        gameRepository.deleteGameByChatId(chatId);

        return CompletableFuture.completedFuture(true);
    }



}
