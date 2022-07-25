package com.example.sixthmafiabot.events.Abstract;


import com.example.sixthmafiabot.services.Abstract.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;


@Slf4j
public abstract class IBaseCustomEvent extends ApplicationEvent {

    protected final Long gameChatId;

    protected final GameService gameService;
    public IBaseCustomEvent(Object source, GameService gameService, Long gameChatId) {

        super(source);
        this.gameChatId = gameChatId;
        this.gameService = gameService;
    }

    public abstract void execute() throws InterruptedException;

}
