package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.DTO.CreateUserDTO;
import com.example.sixthmafiabot.models.User;
import com.example.sixthmafiabot.repository.Abstract.BaseRepository;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
@Slf4j
public class UserRepository extends BaseRepository {

    @Autowired
    SpringUserRepository springUserRepository;

    public User create(CreateUserDTO createUserDTO) {

        User user = modelMapper.map(createUserDTO, User.class);

        springUserRepository.save(user);
        return user;
    }

    public User getUserByTelegramId(Long telegramId) {

        return springUserRepository.getUserByTelegramId(telegramId);
    }



}
