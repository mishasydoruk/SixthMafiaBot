package com.example.sixthmafiabot;

import com.example.sixthmafiabot.DTO.CreateUserDTO;
import com.example.sixthmafiabot.models.User;
import com.example.sixthmafiabot.repository.Abstract.SpringRepositoryImplementations.SpringEnvironmentRepository;
import com.example.sixthmafiabot.services.GameService;
import com.example.sixthmafiabot.services.UserService;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Getter
@Component
public class Bot extends TelegramLongPollingBot {

    private final String botUsername;

    private final String botToken;

    @Autowired
    GameService gameService;

    @Autowired
    UserService userService;

    ExecutorService service = Executors.newFixedThreadPool(10);

    public Bot(TelegramBotsApi telegramBotsApi,
               @Value("${telegram-bot.name}") String botUsername,
               @Value("${telegram-bot.token}") String token ) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = token;

        telegramBotsApi.registerBot(this);
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

    }
}
