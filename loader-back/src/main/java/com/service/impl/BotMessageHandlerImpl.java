package com.service.impl;

import com.bot.Bot;
import com.bot.BotModel;
import com.bot.MessagesPackage;
import com.model.Question;
import com.model.order.Order;
import com.model.order.Status;
import com.model.user.BotUser;
import com.model.user.Customer;
import com.model.user.Porter;
import com.service.*;
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
    @Autowired
    private PorterService porterService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @Override
    public MessagesPackage handleMessage(Update update) {
        MessagesPackage messagesPackage = new MessagesPackage();
        Message message = update.getMessage();
        User user = message == null ? update.getCallbackQuery().getFrom() : message.getFrom();
        BotUser botUser = userService.findTelegramUserByTelegramId(user.getId());
        if (botUser == null) {
            if (update.hasCallbackQuery()) {
                callbackScenario(messagesPackage, update.getCallbackQuery(), null);
            } else {
                anonymousHelloScenario(messagesPackage, message.getChatId());
            }
        } else {
            if (botUser instanceof Porter) {
                Porter porter = (Porter) botUser;
                if (porter.isFinishedAskingQuestions()) {
                    callbackScenario(messagesPackage, update.getCallbackQuery(), porter);
                } else {
                    if ((porter.isAskingQuestions()) && (!porter.isFinishedAskingQuestions())) {
                        answerService.savePorterAnswer(porter, message.getText());
                        Question question = questionService.getNextQuestionForPorter(porter);
                        if (question == null) {
                            porterService.setFinishAskingQuestions(porter);
                            scenarioForKnownPorter(messagesPackage, porter);
                        } else {
                            customSendMessage(messagesPackage, question.getText(), porter.getChatId(), null);
                        }
                    } else {
                        //todo: действия, когда грезчик не закончил регистрацию
                    }
                }
            } else {
                Customer customer = (Customer) botUser;
                if (customer.isFinishedAskingQuestions()) {
                    if (customer.isOrderCreationProcessing()) {
                        setValuesToOrder(customer, message.getText());
                        orderCreationActionHandler(messagesPackage, customer);
                    } else {
                        callbackScenario(messagesPackage, update.getCallbackQuery(), customer);
                    }
                } else {
                    if ((customer.isAskingQuestions()) && (!customer.isFinishedAskingQuestions())) {
                        answerService.saveCustomerAnswer(customer, message.getText());
                        Question question = questionService.getNextQuestionForCustomer(customer);
                        if (question == null) {
                            customerService.setFinishAskingQuestions(customer);
                            scenarioForKnownCustomer(messagesPackage, customer);
                        } else {
                            customSendMessage(messagesPackage, question.getText(), customer.getChatId(), null);
                        }
                    } else {
                        //todo:действия, когда клиент не закончил регистрацию
                    }
                }
            }
        }
        return messagesPackage;
    }

    private void setValuesToOrder(Customer customer, String answer) {
        Order order = orderService.findOrdersByCustomerAndStatus(customer, Status.PROCESSING).stream().findFirst().orElse(null);
        switch (customer.getOrderQuestionNum()) {
            case 0: {
                orderService.setDateForOrder(order, answer);
                break;
            }
            case 1: {
                orderService.setAmountOfPortersForOrder(order, answer);
                break;
            }
            case 2: {
                orderService.setTimeForExecutingOrder(order, answer);
                break;
            }
            case 3: {
                orderService.setPayForHourOfOnePersonforOrder(order, answer);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void orderCreationActionHandler(MessagesPackage messagesPackage, Customer customer) {
        Integer orderQuestionNum = customer.getOrderQuestionNum();
        if (orderQuestionNum + 1 >= BotModel.OrderCreationQuestions.CREATE_ORDER_QUESTIONS.size()) {
            customerService.setOrderCreationProcessing(customer, false);
            customSendMessage(messagesPackage, BotModel.Messages.ORDER_CREATION_FINISHED, customer.getChatId(), BotModel.InlineKeyboards.SELECT_CUSTOMER_ACTION_KEYBOARD);
            return;
        }
        orderQuestionNum++;
        customerService.updateOrderCreationQuestionNum(customer, orderQuestionNum);
        String text = BotModel.OrderCreationQuestions.CREATE_ORDER_QUESTIONS.get(orderQuestionNum);
        customSendMessage(messagesPackage, text, customer.getChatId(), null);
    }

    private void callbackScenario(MessagesPackage messagesPackage, CallbackQuery callbackQuery, BotUser botUser) {
        String command = callbackQuery.getData();
        switch (command) {
            case BotModel.InlineButtons.Commands.SELECT_PORTER_CMD: {
                LOGGER.info("\nSELECT_PORTER_CMD\n");
                callBackSelectPorterHandler(messagesPackage, callbackQuery.getFrom(), callbackQuery.getMessage().getChatId());
                break;
            }
            case BotModel.InlineButtons.Commands.SELECT_CUSTOMER_CMD: {
                LOGGER.info("\nSELECT_CUSTOMER_CMD\n");
                callBackSelectCustomerHandler(messagesPackage, callbackQuery.getFrom(), callbackQuery.getMessage().getChatId());
                break;
            }
            case BotModel.InlineButtons.Commands.CUSTOMER_MAKE_ORDER_CMD: {
                callBackCustomerMakeOrderHandler(messagesPackage, (Customer) botUser);
                break;
            }
            default: {
                LOGGER.info("\nEGORKA = DEFAULT\n");
                break;
            }
        }
    }

    private void callBackCustomerMakeOrderHandler(MessagesPackage messagesPackage, Customer customer) {
        customerService.setOrderCreationProcessing(customer, true);
        customSendMessage(messagesPackage, BotModel.OrderCreationQuestions.CREATE_ORDER_QUESTIONS.get(0), customer.getChatId(), null);
        orderService.createOrder(customer);
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

    private void scenarioForKnownCustomer(MessagesPackage messagesPackage, Customer customer) {
        customSendMessage(messagesPackage, String.format(BotModel.Messages.SELECT_ACTIONS, customer.getName()), customer.getChatId(), BotModel.InlineKeyboards.SELECT_CUSTOMER_ACTION_KEYBOARD);
    }

    private void scenarioForKnownPorter(MessagesPackage messagesPackage, Porter porter) {
        customSendMessage(messagesPackage, String.format(BotModel.Messages.SELECT_ACTIONS, porter.getFullName()), porter.getChatId(), BotModel.InlineKeyboards.SELECT_PORTER_ACTION_KEYBOARD);
    }

    private void customSendMessage(MessagesPackage messagesPackage, String text, Long chatId, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        messagesPackage.addMessageToPackage(sendMessage);
    }
}
