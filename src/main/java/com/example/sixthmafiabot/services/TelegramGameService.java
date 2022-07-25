package com.example.sixthmafiabot.services;

import com.example.sixthmafiabot.Bot;
import com.example.sixthmafiabot.events.GameCreatedEvent;
import com.example.sixthmafiabot.services.Abstract.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramGameService extends GameService {

    @Autowired
    Bot bot;

    @Override
    public void notifyPlayer(Long chatId, String msg) {
        bot.sendMessage(chatId, msg);
    }
}
