package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.DTO.CreateUserDTO;
import com.example.sixthmafiabot.DTO.UpdatePlayerDTO;
import com.example.sixthmafiabot.DTO.UpdateUserDTO;
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

        CreateUserDTO validatedCreateUserDTO = userValidator.validateCreate(createUserDTO);

        User user = userRepository.create(validatedCreateUserDTO);

        return CompletableFuture.completedFuture(user);
    }

    @Async("serviceExecutor")
    public CompletableFuture<User> updateUser(User userInDatabase, UpdateUserDTO updateUserDTO) throws ServiceValidationError {

        UpdateUserDTO validatedUpdateUserDTO = userValidator.validateUpdate(updateUserDTO);

        User updatedUser = userRepository.update(userInDatabase, validatedUpdateUserDTO);

        return CompletableFuture.completedFuture(updatedUser);
    }

    @Async("serviceExecutor")
    public CompletableFuture<User> getUserByTelegramId(Long userId) {

        User user = userRepository.getUserByTelegramId(userId);

        return CompletableFuture.completedFuture(user);
    }

}