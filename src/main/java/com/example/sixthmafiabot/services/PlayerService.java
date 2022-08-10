package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.DTO.CreatePlayerDTO;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.Player;
import com.example.sixthmafiabot.services.Abstract.BaseService;
import com.example.sixthmafiabot.validators.PlayerValidator;
import com.example.sixthmafiabot.repository.PlayerRepository;
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

    @Async("serviceExecutor")
    public CompletableFuture<Player> createPlayer(CreatePlayerDTO player) throws ServiceValidationError {

        CompletableFuture<CreatePlayerDTO> validatedPlayer = playerValidator.validateCreate(player);

        return validatedPlayer
                .thenCompose(valPlayer -> playerRepository.create(valPlayer));

    }

    @Async("serviceExecutor")
    public CompletableFuture<Player> getPlayerByUserId(Long userTelegramId)  {

        return playerRepository.getPlayerByUserTelegramId(userTelegramId);
    }

}
