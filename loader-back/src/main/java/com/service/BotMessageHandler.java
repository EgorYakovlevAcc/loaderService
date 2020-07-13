package com.service;

import com.bot.MessagesPackage;
import org.telegram.telegrambots.api.objects.Update;

public interface BotMessageHandler {
    MessagesPackage handleMessage(Update update);
}
