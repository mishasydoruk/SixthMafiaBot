package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.DTO.CreateEnvironmentDTO;
import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.repository.Abstract.BaseRepository;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringEnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public class EnvironmentRepository extends BaseRepository {

    @Autowired
    SpringEnvironmentRepository springEnvironmentRepository;

    @Async("repoExecutor")
    public CompletableFuture<Environment> create(CreateEnvironmentDTO createEnvironmentDTO){

        Environment env = modelMapper.map(createEnvironmentDTO, Environment.class);

        springEnvironmentRepository.save(env);

        return CompletableFuture.completedFuture(env);

    }

    @Async("repoExecutor")
    public CompletableFuture<Environment> getEnvironmentByChatId(Long chatId){

        return springEnvironmentRepository.getEnvironmentByChatId(chatId);
    }
}
