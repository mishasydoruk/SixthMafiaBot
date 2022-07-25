package com.example.sixthmafiabot.events;

import com.example.sixthmafiabot.events.Abstract.IBaseCustomEvent;
import com.example.sixthmafiabot.models.Game;
import com.example.sixthmafiabot.services.Abstract.GameService;

import java.util.concurrent.TimeUnit;

public class GameCreatedEvent extends IBaseCustomEvent {

    public GameCreatedEvent(Object source, GameService gameService, Long gameChatId) {

        super(source, gameService, gameChatId);
    }


    @Override
    public void execute() throws InterruptedException {

        TimeUnit.SECONDS.sleep(25);

        gameService.startGameByChatId(gameChatId);
    }
}
