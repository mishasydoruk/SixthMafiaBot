package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.DTO.CreatePlayerDTO;
import com.example.sixthmafiabot.DTO.UpdatePlayerDTO;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.models.Player;
import com.example.sixthmafiabot.models.User;
import com.example.sixthmafiabot.repository.Abstract.BaseRepository;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepository extends BaseRepository {

    @Autowired
    SpringPlayerRepository springPlayerRepository;

    public Player create(CreatePlayerDTO playerDTO){

        Player player = modelMapper.map(playerDTO, Player.class);

        springPlayerRepository.save(player);

        return player;
    }

    public Player update(Player playerInDatabase, UpdatePlayerDTO updatePlayerDTO){

        modelMapper.map(updatePlayerDTO, playerInDatabase);

        springPlayerRepository.save(playerInDatabase);

        return playerInDatabase;
    }

    public Player getPlayerByUser(User user){

        return springPlayerRepository.getPlayerByUser(user);
    }


    public Player getPlayerByUserTelegramId(Long tgId){

        return springPlayerRepository.findPlayerByUserTelegramId(tgId);
    }

    @Async("repoExecutor")
    public void deletePlayersByGame(Game game){

        springPlayerRepository.deletePlayersByGame(game);
    }


}
