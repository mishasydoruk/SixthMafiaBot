package com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations;

import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.models.Player;
import com.example.sixthmafiabot.models.User;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface SpringPlayerRepository extends Repository<Player, Long> {

    @Async("asyncExecutor")
    void save(Player player);

    @Async("asyncExecutor")
    CompletableFuture<Player> getPlayerByUser(User user);

    @Async("asyncExecutor")
    void deletePlayersByGame(Game game);

}
