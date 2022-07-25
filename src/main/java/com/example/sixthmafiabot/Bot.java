package com.example.sixthmafiabot;

import com.example.sixthmafiabot.services.GameService;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
@Component
public class Bot extends TelegramLongPollingBot {

    private final String botUsername;

    private final String botToken;

    @Autowired
    GameService gameService;

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

        Message requestMessage = update.getMessage();

        if(requestMessage.getText().equals("/create")){

            CompletableFuture<Boolean> gameAlreadyCreated = gameService.createGame(requestMessage.getChatId());


            if(gameAlreadyCreated.join()){
                service.submit(() -> waitAndThenStartGame(requestMessage.getChatId()));
                sendMessage(requestMessage.getChatId(), "Registration started: ");
            }
            else{
                if(gameService.gameIsStarted(requestMessage.getChatId()).join()){
                    sendMessage(requestMessage.getChatId(), "Game already created!");
                }
            }
        }

        if(requestMessage.getText().equals("/start")){

            CompletableFuture<Boolean> gameStarted
                    = gameService.startGameByChatId(requestMessage.getChatId());

            if(gameStarted.join()){
                sendMessage(requestMessage.getChatId(), "Game is starting now!");
            }

        }

        if(requestMessage.getText().equals("/cancel")){

            CompletableFuture<Boolean> registrationCanceled
                    = gameService.cancelRegistration(requestMessage.getChatId());

            if(registrationCanceled.join()){
                sendMessage(requestMessage.getChatId(), "Registration canceled!");
            }
        }
    }


    public void waitAndThenStartGame(Long chatId) {

        try{
            TimeUnit.SECONDS.sleep(gameService.getRegistrationTime(chatId).join());
        }
        catch (InterruptedException e){
            log.warn(Arrays.toString(e.getStackTrace()));
        }

        if(!gameService.gameIsStarted(chatId).join()){
            gameService.startGameByChatId(chatId);
            sendMessage(chatId, "Game is starting now!");
        }

    }


    public void sendMessage(Long chatId, String text){

        SendMessage response = new SendMessage();

        response.setChatId(chatId.toString());
        response.setText(text);

        try {
            execute(response);
        }
        catch (TelegramApiException e){
            log.warn(e.getMessage());
        }

    }



}
