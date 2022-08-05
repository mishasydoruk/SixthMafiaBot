package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.exceptions.NotFoundException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.Environment;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringEnvironmentRepository;
import com.example.sixthmafiabot.services.Abstract.BaseService;
import com.example.sixthmafiabot.validators.EnvironmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
public class EnvironmentService implements BaseService {

    @Autowired
    EnvironmentValidator environmentValidator;

    @Autowired
    SpringEnvironmentRepository environmentRepository;

    public CompletableFuture<Environment> createEnvironment(Environment env) throws ServiceValidationError {

        Environment validatedEnvironment = environmentValidator.validateCreate(env).join();

        environmentRepository.save(validatedEnvironment);

        return CompletableFuture.completedFuture(validatedEnvironment);

    }

    public CompletableFuture<Environment> getEnvironment(Long chatId) throws NotFoundException {

        Environment env = environmentRepository.getEnvironmentByChatId(chatId).join();

        if(env == null){

            throw new NotFoundException("No environment with chatId = " +chatId);
        }

        return CompletableFuture.completedFuture(env);
    }

    public CompletableFuture<Environment> getOrCreateIfNotExists(Long chatId) throws ServiceValidationError {

        CompletableFuture<Environment> envToReturn;

        try{

            envToReturn = getEnvironment(chatId);
        }
        catch (NotFoundException ex){

            Environment env = new Environment(chatId);
            envToReturn = createEnvironment(env);
        }

        return envToReturn;
    }
}
