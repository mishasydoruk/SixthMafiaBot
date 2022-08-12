package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.DTO.CreateEnvironmentDTO;
import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.repository.Abstract.BaseRepository;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringEnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public class EnvironmentRepository extends BaseRepository {

    @Autowired
    SpringEnvironmentRepository springEnvironmentRepository;

    public Environment create(CreateEnvironmentDTO createEnvironmentDTO){

        Environment env = modelMapper.map(createEnvironmentDTO, Environment.class);

        springEnvironmentRepository.save(env);

        return env;

    }

    @Async("repoExecutor")
    public Environment getEnvironmentByChatId(Long chatId){

        return springEnvironmentRepository.getEnvironmentByChatId(chatId);
    }
}
