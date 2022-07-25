package com.example.sixthmafiabot.events;

import com.example.sixthmafiabot.events.Abstract.IBaseCustomEvent;
import com.example.sixthmafiabot.services.Abstract.GameService;

public class AllTasksCompletedEvent extends IBaseCustomEvent {

    public AllTasksCompletedEvent(Object source, GameService gameService, Long gameChatId) {
        super(source, gameService, gameChatId);
    }

    @Override
    public void execute() throws InterruptedException {

    }


}
