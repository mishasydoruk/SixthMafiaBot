package com.example.sixthmafiabot.services.Abstract;


import com.example.sixthmafiabot.events.Abstract.IBaseCustomEvent;
import com.example.sixthmafiabot.events.GameCreatedEvent;
import com.example.sixthmafiabot.events.handlers.EventPublisher;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.services.Abstract.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public abstract class GameService implements BaseService {

    private Map<Long, Game> activeGames = new HashMap<>();

    @Autowired
    private EventPublisher eventPublisher;

    public void createGame(Long chatId){

        if(activeGames.get(chatId) == null){

            Game newGame = new Game(this, eventPublisher, chatId);
            activeGames.put(chatId, newGame);

            eventPublisher.publishCustomEvent(new GameCreatedEvent(eventPublisher, this, chatId));
        }
    }

    public void startGameByChatId(Long chatId){

        if(activeGames.get(chatId) != null){

            activeGames.get(chatId).start();
        }
        else {
            notifyPlayer(chatId, "No game created!");
        }
    }

    public void cancelRegistration(Long chatId){

        Game game = activeGames.get(chatId);

        if(!game.isStarted()){
            activeGames.remove(chatId);
            notifyPlayer(chatId, "Registration canceled!");
        }
    }


    public abstract void notifyPlayer(Long chatId, String msg);

}
