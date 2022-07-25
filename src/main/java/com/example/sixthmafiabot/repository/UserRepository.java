package com.example.sixthmafiabot.repository;

import com.example.sixthmafiabot.models.User;
import org.springframework.data.repository.Repository;

import java.util.UUID;


public interface UserRepository extends Repository<User, Long> {

    void save(User user);

    User getUserByTelegramId(Long telegramId);

}
