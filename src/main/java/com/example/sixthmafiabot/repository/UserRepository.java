package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.DTO.CreateUserDTO;
import com.example.sixthmafiabot.models.User;
import com.example.sixthmafiabot.repository.Abstract.BaseRepository;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository

@Slf4j
public class UserRepository implements BaseRepository {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    SpringUserRepository springUserRepository;

    public CompletableFuture<User> create(CreateUserDTO createUserDTO) {

        User user = modelMapper.map(createUserDTO, User.class);

        springUserRepository.save(user);
        return CompletableFuture.completedFuture(user);
    }


    public CompletableFuture<User> getUserByTelegramId(Long telegramId) {

        User user = springUserRepository.getUserByTelegramId(telegramId).join();

        return CompletableFuture.completedFuture(user);
    }

}
