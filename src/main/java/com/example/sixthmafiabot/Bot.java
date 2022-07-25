package com.example.sixthmafiabot;

import com.example.sixthmafiabot.services.TelegramGameService;
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

@Slf4j
@Getter
@Component
public class Bot extends TelegramLongPollingBot {

    private final String botUsername;

    private final String botToken;

    @Autowired
    TelegramGameService gameService;

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
            gameService.createGame(requestMessage.getChatId());
        }

        if(requestMessage.getText().equals("/start")){
            gameService.startGameByChatId(requestMessage.getChatId());
        }

        if(requestMessage.getText().equals("/cancel")){
            gameService.cancelRegistration(requestMessage.getChatId());
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
