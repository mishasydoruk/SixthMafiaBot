package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.exceptions.NotFoundException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.Player;
import com.example.sixthmafiabot.models.User;
import com.example.sixthmafiabot.repository.PlayerRepository;
import com.example.sixthmafiabot.repository.UserRepository;
import com.example.sixthmafiabot.services.Abstract.BaseService;
import com.example.sixthmafiabot.validators.PlayerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PlayerService implements BaseService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    UserService userService;

    @Autowired
    PlayerValidator playerValidator;

    @Async("asyncExecutor")
    public CompletableFuture<Player> createPlayer(Player player) throws ServiceValidationError {

        Player validatedPlayer = playerValidator.validateCreate(player).join();

        playerRepository.save(validatedPlayer);

        return CompletableFuture.completedFuture(validatedPlayer);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Player> getPlayerByUserId(Long userTelegramId) throws NotFoundException {

        User user = userService.getUserByTelegramId(userTelegramId).join();

        Player player = playerRepository.getPlayerByUser(user).join();

        if(player == null){
            throw new NotFoundException("No player with user telegram id = " + userTelegramId);
        }

        return CompletableFuture.completedFuture(player);
    }

}
