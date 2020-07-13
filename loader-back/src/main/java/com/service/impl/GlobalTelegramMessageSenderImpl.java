package com.service.impl;

import com.bot.Bot;
import com.model.user.Porter;
import com.service.GlobalTelegramMessageSender;
import com.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GlobalTelegramMessageSenderImpl implements GlobalTelegramMessageSender {
    public static final Logger LOGGER = LoggerFactory.getLogger(GlobalTelegramMessageSenderImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private Bot bot;

    @Async
    @Override
    public void sendGlobalMessage(String text, Integer minScore, Integer maxScore) {
    }
}
