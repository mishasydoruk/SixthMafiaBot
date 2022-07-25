package com.example.sixthmafiabot.models;

import com.example.sixthmafiabot.events.GameStartedEvent;
import com.example.sixthmafiabot.events.handlers.EventPublisher;
import com.example.sixthmafiabot.models.Abstract.BaseModel;
import com.example.sixthmafiabot.services.Abstract.GameService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class Game extends BaseModel {

    private List<Player> players = new ArrayList<>();
    @NotNull
    private final Long chatId;
    private boolean started = false;

    private final EventPublisher eventPublisher;

    private final GameService gameService;


    public Game(GameService gameService, EventPublisher eventPublisher, Long chatId){

        this.gameService = gameService;
        this.eventPublisher = eventPublisher;
        this.chatId = chatId;

        log.info("Created game in chat with id = " + chatId);
        gameService.notifyPlayer(chatId, "Registration started!");
    }

    public void start(){

        if(!started){
            started = true;
            eventPublisher.publishCustomEvent(new GameStartedEvent(eventPublisher, gameService, chatId));
            log.info("Game started!");
            gameService.notifyPlayer(chatId, "The game has begun!");
        }
        else{
            log.warn(String.format("Game in chat with id = %s is already started!", chatId));
        }

    }


}
