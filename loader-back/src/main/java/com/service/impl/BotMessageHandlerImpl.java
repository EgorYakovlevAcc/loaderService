package com.service.impl;

import com.bot.Bot;
import com.bot.BotModel;
import com.bot.MessagesPackage;
import com.model.user.BotUser;
import com.model.user.Customer;
import com.model.user.Porter;
import com.service.AnswerService;
import com.service.BotMessageHandler;
import com.service.QuestionService;
import com.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
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
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @Override
    public MessagesPackage handleMessage(Update update) {
        MessagesPackage messagesPackage = new MessagesPackage();
        Message message = update.getMessage();
        User user = message.getFrom();
        LOGGER.info("EGORKA = {}", user);
        BotUser botUser = userService.findTelegramUserByTelegramId(user.getId());
        if (botUser == null) {
            if (update.hasCallbackQuery()) {
                callbackScenario(messagesPackage, update.getCallbackQuery());
            } else {
                anonymousHelloScenario(messagesPackage, message.getChatId());
            }
        } else {
            if (botUser instanceof Porter) {
                Porter porter = (Porter) botUser;
                if ((porter.isAskingQuestions()) && (!porter.isFinishedAskingQuestions())) {
                    answerService.savePorterAnswer(porter, message.getText());
                    customSendMessage(messagesPackage, questionService.getNextQuestionForPorter(porter).getText(), porter.getChatId(), null);
                }
                else {
                    knownHelloScenarioForPorter(messagesPackage, porter);
                }
            } else {
                Customer customer = (Customer) botUser;
                if ((customer.isAskingQuestions()) && (!customer.isFinishedAskingQuestions())) {
                    answerService.saveCustomerAnswer(customer, message.getText());
                    customSendMessage(messagesPackage, questionService.getNextQuestionForCustomer(customer).getText(), customer.getChatId(), null);
                }
                else {
                    knownHelloScenarioForCustomer(messagesPackage, customer);
                }
            }
        }
        return messagesPackage;
    }

    private void textScenario() {

    }

    private void callbackScenario(MessagesPackage messagesPackage, CallbackQuery callbackQuery) {
        String command = callbackQuery.getData();
        switch (command) {
            case BotModel.InlineButtons.Commands.SELECT_PORTER_CMD: {
                callBackSelectPorterHandler(messagesPackage, callbackQuery.getFrom(), callbackQuery.getMessage().getChatId());
                break;
            }
            case BotModel.InlineButtons.Commands.SELECT_CUSTOMER_CMD: {
                callBackSelectCustomerHandler(messagesPackage, callbackQuery.getFrom(), callbackQuery.getMessage().getChatId());
                break;
            }
            default: {
                break;
            }
        }
    }

    private void callBackSelectPorterHandler(MessagesPackage messagesPackage, User user, Long chatId) {
        Porter porter = userService.createPorter(user.getId(), chatId);
        customSendMessage(messagesPackage, questionService.getNextQuestionForPorter(porter).getText(), chatId, null);
    }

    private void callBackSelectCustomerHandler(MessagesPackage messagesPackage, User user, Long chatId) {
        Customer customer = userService.createCustomer(user.getId(), chatId);
        customSendMessage(messagesPackage, questionService.getNextQuestionForCustomer(customer).getText(), chatId, null);
    }

    private void anonymousHelloScenario(MessagesPackage messagesPackage, Long chatId) {
        SendMessage anonymousHelloMessage = new SendMessage();
        anonymousHelloMessage.setText(BotModel.Messages.HELLO_ANONYMOUS);
        anonymousHelloMessage.setChatId(chatId);
        anonymousHelloMessage.setReplyMarkup(BotModel.InlineKeyboards.SELECT_ROLE_KEYBOARD);
        messagesPackage.addMessageToPackage(anonymousHelloMessage);
    }

    private void knownHelloScenarioForCustomer(MessagesPackage messagesPackage, Customer customer) {
        customSendMessage(messagesPackage, String.format(BotModel.Messages.HELLO_KNOWN, customer.getName()), customer.getChatId(), BotModel.InlineKeyboards.SELECT_CUSTOMER_ACTION_KEYBOARD);
    }

    private void knownHelloScenarioForPorter(MessagesPackage messagesPackage, Porter porter) {
        customSendMessage(messagesPackage, String.format(BotModel.Messages.HELLO_KNOWN, porter.getFullName()), porter.getChatId(), BotModel.InlineKeyboards.SELECT_PORTER_ACTION_KEYBOARD);
    }

    private void customSendMessage(MessagesPackage messagesPackage, String text, Long chatId, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        messagesPackage.addMessageToPackage(sendMessage);
    }
}
