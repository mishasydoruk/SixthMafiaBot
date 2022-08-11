package com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations;

import com.example.sixthmafiabot.models.User;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


public interface SpringUserRepository extends Repository<User, Long>{

    @Async("repoExecutor")
    void save(User user);

    User getUserByTelegramId(Long telegramId);
}
