package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.DTO.CreateGameDTO;
import com.example.sixthmafiabot.DTO.UpdateGameDTO;
import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.repository.Abstract.BaseRepository;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository extends BaseRepository {

    @Autowired
    SpringGameRepository springGameRepository;

    public Game create(CreateGameDTO createGameDTO) {

        Game game = modelMapper.map(createGameDTO, Game.class);

        springGameRepository.save(game);

        return game;
    }

    public Game update(Game gameInDatabase, UpdateGameDTO updateGameDTO){

        gameInDatabase.setGameStatus(updateGameDTO.getGameStatus());

        springGameRepository.save(gameInDatabase);

        return gameInDatabase;
    }

    public Game getGameByEnvironment(Environment env){

        return springGameRepository.getGameByEnvironment(env);
    }

    public Game getGameByEnvironmentChatId(Long chatId){

        return springGameRepository.findGameByEnvironmentChatId(chatId);
    }

    public void deleteGameByEnvironment(Environment env){

        springGameRepository.deleteGameByEnvironment(env);
    }

}
