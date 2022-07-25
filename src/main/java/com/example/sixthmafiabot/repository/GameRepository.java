package com.example.sixthmafiabot.repository;


import com.example.sixthmafiabot.models.Game;
import org.springframework.data.repository.Repository;

import javax.transaction.Transactional;

public interface GameRepository extends Repository<Game, Long> {


    @Transactional
    void save(Game game);

    Game getGameByChatId(Long chatId);

    @Transactional
    void deleteGameByChatId(Long chatId);
}
