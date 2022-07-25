package com.example.sixthmafiabot.events.handlers;


import com.example.sixthmafiabot.events.Abstract.IBaseCustomEvent;
import com.example.sixthmafiabot.events.GameCreatedEvent;
import com.example.sixthmafiabot.services.Abstract.GameService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventListener implements ApplicationListener<IBaseCustomEvent> {

    @Autowired
    GameService gameService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(IBaseCustomEvent event) {
        event.execute();
    }
}
