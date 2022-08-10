package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.DTO.CreateGameDTO;
import com.example.sixthmafiabot.DTO.CreateUserDTO;
import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.models.User;
import com.example.sixthmafiabot.repository.Abstract.BaseRepository;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@Repository
public class GameRepository extends BaseRepository {

    @Autowired
    SpringGameRepository springGameRepository;

    @Async("repoExecutor")
    public CompletableFuture<Game> create(CreateGameDTO createGameDTO) {

        Game game = modelMapper.map(createGameDTO, Game.class);

        springGameRepository.save(game);

        return CompletableFuture.completedFuture(game);
    }

    @Async("repoExecutor")
    public CompletableFuture<Game> getGameByEnvironment(Environment env){

        return springGameRepository.getGameByEnvironment(env);
    }

    @Async("repoExecutor")
    public CompletableFuture<Game> getGameByEnvironmentChatId(Long chatId){

        return springGameRepository.findGameByEnvironmentChatId(chatId);
    }

    @Async("repoExecutor")
    @Transactional
    public void deleteGameByEnvironment(Environment env){

        springGameRepository.deleteGameByEnvironment(env);
    }

}
