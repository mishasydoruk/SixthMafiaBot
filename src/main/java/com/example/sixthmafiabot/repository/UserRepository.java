package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.DTO.UserDTO;
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
    SpringUserRepository userRepository;

    public CompletableFuture<User> create(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        save(user);
        return CompletableFuture.completedFuture(user);
    }

    public void save(User user) {

        userRepository.save(user);
    }

    public CompletableFuture<User> getUserByTelegramId(Long telegramId) {

        User user = userRepository.getUserByTelegramId(telegramId).join();

        return CompletableFuture.completedFuture(user);
    }

}
