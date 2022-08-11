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


    Game getGameByEnvironment(Environment env);

    Game findGameByEnvironmentChatId(Long chatId);


    @Async("repoExecutor")
    @Transactional
    void deleteGameByEnvironment(Environment env);
}
