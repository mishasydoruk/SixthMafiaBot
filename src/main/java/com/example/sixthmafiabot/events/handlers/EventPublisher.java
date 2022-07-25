package com.example.sixthmafiabot.events.handlers;


import com.example.sixthmafiabot.events.Abstract.IBaseCustomEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component("eventPublisher")
public class EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(IBaseCustomEvent event){

        applicationEventPublisher.publishEvent(event);
    }
}
