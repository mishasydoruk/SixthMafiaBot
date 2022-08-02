package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.models.User;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;


public interface UserRepository extends Repository<User, Long> {


    @Async("asyncExecutor")
    void save(User user);

    @Async("asyncExecutor")
    CompletableFuture<User> getUserByTelegramId(Long telegramId);

}
