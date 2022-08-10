package com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations;


import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.models.Game;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

public interface SpringGameRepository extends Repository<Game, Long> {

    @Async("repoExecutor")
    @Transactional
    void save(Game game);

    @Async("repoExecutor")
    CompletableFuture<Game> getGameByEnvironment(Environment env);

    @Async("repoExecutor")
    CompletableFuture<Game> findGameByEnvironmentChatId(Long chatId);


    @Async("repoExecutor")
    @Transactional
    void deleteGameByEnvironment(Environment env);
}
