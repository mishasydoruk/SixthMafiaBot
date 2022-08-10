package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.DTO.CreateUserDTO;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.User;
import com.example.sixthmafiabot.repository.UserRepository;
import com.example.sixthmafiabot.services.Abstract.BaseService;
import com.example.sixthmafiabot.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserService implements BaseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserValidator userValidator;

    @Async("serviceExecutor")
    public CompletableFuture<User> createUser(CreateUserDTO createUserDTO) throws ServiceValidationError {

        CompletableFuture<CreateUserDTO> validatedCreateUserDTO = userValidator.validateCreate(createUserDTO);

        User user = validatedCreateUserDTO
                .thenCompose(userData -> userRepository.create(userData)).join();

        return CompletableFuture.completedFuture(user);
    }

    @Async("serviceExecutor")
    public CompletableFuture<User> getUserByTelegramId(Long userId) {

        return userRepository.getUserByTelegramId(userId);
    }

}