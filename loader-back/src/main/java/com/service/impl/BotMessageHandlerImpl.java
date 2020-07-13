package com.service.impl;

import com.bot.Bot;
import com.bot.BotModel;
import com.bot.MessagesPackage;
import com.model.user.BotUser;
import com.model.user.Customer;
import com.model.user.Porter;
import com.service.BotMessageHandler;
import com.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
@Transactional
public class BotMessageHandlerImpl implements BotMessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);
    @Autowired
    private UserService userService;

    @Override
    public MessagesPackage handleMessage(Update update) {
        MessagesPackage messagesPackage = new MessagesPackage();
        Message message = update.getMessage();
        User user = message.getFrom();
        BotUser botUser = userService.findTelegramUserByTelegramId(user.getId());
        if (botUser == null) {
            anonymousHelloScenario(messagesPackage, message.getChatId());
        }
        else {
            knownHelloScenario(messagesPackage, botUser);
        }
        return messagesPackage;
    }

    private void textScenario() {

    }

    private void callbackScenario() {

    }

    private void anonymousHelloScenario(MessagesPackage messagesPackage, Long chatId) {
        SendMessage anonymousHelloMessage = new SendMessage();
        anonymousHelloMessage.setText(BotModel.Messages.HELLO_ANONYMOUS);
        anonymousHelloMessage.setChatId(chatId);
        anonymousHelloMessage.setReplyMarkup(BotModel.InlineKeyboards.SELECT_ROLE_KEYBOARD);
        messagesPackage.addMessageToPackage(anonymousHelloMessage);
    }

    private void knownHelloScenario(MessagesPackage messagesPackage, BotUser botUser) {
        if (botUser instanceof Customer) {
            Customer customer = (Customer) botUser;
            customSendMessage(messagesPackage, String.format(BotModel.Messages.HELLO_KNOWN, customer.getName()), customer.getChatId(), BotModel.InlineKeyboards.SELECT_CUSTOMER_ACTION_KEYBOARD);
        }
        else {
            Porter porter = (Porter) botUser;
            customSendMessage(messagesPackage, String.format(BotModel.Messages.HELLO_KNOWN, porter.getFullName()), porter.getChatId(), BotModel.InlineKeyboards.SELECT_PORTER_ACTION_KEYBOARD);
        }
    }

    private void customSendMessage(MessagesPackage messagesPackage, String text, Long chatId, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        messagesPackage.addMessageToPackage(sendMessage);
    }
}
