package com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations;

import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.models.Player;
import com.example.sixthmafiabot.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

public interface SpringPlayerRepository extends Repository<Player, Long> {

    @Async("repoExecutor")
    void save(Player player);

    @Async("repoExecutor")
    CompletableFuture<Player> getPlayerByUser(User user);

    @Async("repoExecutor")
    CompletableFuture<Player> findPlayerByUserTelegramId(Long telegramId);


    @Async("repoExecutor")
    @Transactional
    void deletePlayersByGame(Game game);

}
