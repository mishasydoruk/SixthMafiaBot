package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.DTO.CreateEnvironmentDTO;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.repository.EnvironmentRepository;
import com.example.sixthmafiabot.services.Abstract.BaseService;
import com.example.sixthmafiabot.validators.EnvironmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
public class EnvironmentService implements BaseService {

    @Autowired
    EnvironmentValidator environmentValidator;

    @Autowired
    EnvironmentRepository environmentRepository;

    @Async("serviceExecutor")
    public CompletableFuture<Environment> createEnvironment(CreateEnvironmentDTO createEnvironmentDTO) throws ServiceValidationError {

        CreateEnvironmentDTO validatedEnv = environmentValidator.validateCreate(createEnvironmentDTO);

        Environment environment = environmentRepository.create(validatedEnv);

        return CompletableFuture.completedFuture(environment);
    }

    @Async("serviceExecutor")
    public CompletableFuture<Environment> getEnvironment(Long chatId) {

        Environment environment = environmentRepository.getEnvironmentByChatId(chatId);

        return CompletableFuture.completedFuture(environment);
    }

}
