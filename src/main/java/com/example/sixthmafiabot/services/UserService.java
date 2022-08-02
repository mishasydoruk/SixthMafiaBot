package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.exceptions.NotFoundException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.models.User;
import com.example.sixthmafiabot.repository.UserRepository;
import com.example.sixthmafiabot.services.Abstract.BaseService;
import com.example.sixthmafiabot.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserService implements BaseService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserValidator userValidator;

    @Async("asyncExecutor")
    public CompletableFuture<User> createUser(User user) throws ServiceValidationError {

        User validatedUser = userValidator.validateCreate(user).join();

        userRepository.save(validatedUser);

        return CompletableFuture.completedFuture(validatedUser);
    }

    @Async("asyncExecutor")
    public CompletableFuture<User> getUserByTelegramId(Long userId) throws NotFoundException {

        User user = userRepository.getUserByTelegramId(userId).join();

        if(user == null){
            throw new NotFoundException("No user with id = "+ userId);
        }

        return CompletableFuture.completedFuture(user);
    }

}
