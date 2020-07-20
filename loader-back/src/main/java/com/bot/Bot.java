package com.bot;

import com.service.BotMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.api.methods.groupadministration.ExportChatInviteLink;
import org.telegram.telegrambots.api.methods.send.SendContact;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {
    private boolean isBotActive = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);
    private final static String TURN_OFF_BOT_MESSAGE = "Sorry, I'm turned off. Please, try again later!";
    @Autowired
    private BotMessageHandler botMessageHandler;

    @Override
    public void onUpdateReceived(Update update) {
        LOGGER.info("UPDATE = {}", update);
        if (getIsBotActive()) {
            MessagesPackage answersPackage = botMessageHandler.handleMessage(update);
            try {
                for (PartialBotApiMethod answer: answersPackage.getMessages()) {
                    if (answer instanceof SendMessage) {
                        SendMessage sendAnswer = (SendMessage) answer;
                        execute(sendAnswer); // Call method to send the message
                    } else if (answer instanceof SendPhoto){
                        SendPhoto sendPhoto = (SendPhoto) answer;
                        sendPhoto(sendPhoto); // Call method to send the message
                    }
                }
                for (PartialBotApiMethod contact: answersPackage.getContacts()) {
                    execute(contact);
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            SendMessage answer = new SendMessage();
            answer.setChatId(update.getMessage().getChatId());
            answer.setText(TURN_OFF_BOT_MESSAGE);
            try {
                execute(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "ey_example_bot";
    }

    @Override
    public String getBotToken() {
        return "1100246042:AAGlbQkUw-usTDp_9nUuwJfYJ7VWs6YNclg";
    }

    public boolean getIsBotActive() {
        return this.isBotActive;
    }

    public void setIsBotActive(boolean isBotActive) {
        this.isBotActive = isBotActive;
    }
}
