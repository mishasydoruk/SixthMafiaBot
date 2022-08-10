package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.DTO.CreatePlayerDTO;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.models.Player;
import com.example.sixthmafiabot.models.User;
import com.example.sixthmafiabot.repository.Abstract.BaseRepository;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

@Repository
public class PlayerRepository extends BaseRepository {

    @Autowired
    SpringPlayerRepository springPlayerRepository;

    @Async("repoExecutor")
    public CompletableFuture<Player> create(CreatePlayerDTO playerDTO){

        Player player = modelMapper.map(playerDTO, Player.class);

        springPlayerRepository.save(player);

        return CompletableFuture.completedFuture(player);
    }

    @Async("repoExecutor")
    public CompletableFuture<Player> getPlayerByUser(User user){

        return springPlayerRepository.getPlayerByUser(user);
    }

    @Async("repoExecutor")
    public CompletableFuture<Player> getPlayerByUserTelegramId(Long tgId){

        return springPlayerRepository.findPlayerByUserTelegramId(tgId);
    }


    @Async("repoExecutor")
    @Transactional
    public void deletePlayersByGame(Game game){

        springPlayerRepository.deletePlayersByGame(game);
    }


}