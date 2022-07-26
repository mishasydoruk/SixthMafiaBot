package com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations;

import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.models.Player;
import com.example.sixthmafiabot.models.User;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;

public interface SpringPlayerRepository extends Repository<Player, Long> {

    @Async("repoExecutor")
    void save(Player player);

    Player getPlayerByUser(User user);

    Player findPlayerByUserTelegramId(Long telegramId);

    @Async("repoExecutor")
    @Transactional
    void deletePlayersByGame(Game game);

}
