package com.example.sixthmafiabot.repository;


import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.models.Game;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

public interface GameRepository extends Repository<Game, Long> {

    @Async("asyncExecutor")
    @Transactional
    void save(Game game);

    @Async("asyncExecutor")
    CompletableFuture<Game> getGameByEnvironment(Environment env);

    @Async("asyncExecutor")
    @Transactional
    void deleteGameByChatId(Long chatId);
}
