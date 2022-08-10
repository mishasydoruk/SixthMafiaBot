package com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations;

import com.example.sixthmafiabot.models.Environment;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface SpringEnvironmentRepository extends Repository<Environment, Long> {
    
    @Async("repoExecutor")
    void save(Environment env);

    @Async("repoExecutor")
    CompletableFuture<Environment> getEnvironmentByChatId(Long chatId);

}
