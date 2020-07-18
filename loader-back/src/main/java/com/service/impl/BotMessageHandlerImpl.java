package com.service.impl;

import com.bot.Bot;
import com.bot.BotModel;
import com.bot.MessagesPackage;
import com.exception.CustomBotException;
import com.google.common.collect.ImmutableList;
import com.model.Question;
import com.model.TimeTable;
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
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
                        if (porter.isStartTimetable()) {
                            if (message == null) {
                                callbackScenario(messagesPackage, update.getCallbackQuery(), porter);
                            } else {
                                try {
                                    if (!porter.isHasStartDateInput()) {
                                        String startTime = message.getText();
                                        porterService.setHasStartDateInputOn(porter, startTime);
                                        customSendMessage(messagesPackage, BotModel.InlineButtons.Texts.PORTER_FINISH_DATE, porter.getChatId(), null);
                                    } else {
                                        String finishTime = message.getText();
                                        TimeTable timeTable = porterService.setHasStartDateInputOff(porter, finishTime);
                                        customSendMessage(messagesPackage, String.format(BotModel.InlineButtons.Texts.DAY_TIMETABLE_RESULT, timeTable.getDay(), timeTable.getStart(), timeTable.getFinish()), porter.getChatId(), BotModel.InlineKeyboards.PORTER_TIMETABLE_ACTION_KEYBOARD);
                                    }
                                } catch (CustomBotException cbe) {
                                    customSendMessage(messagesPackage, BotModel.InlineButtons.Texts.PORTER_INVALID_TIME_FORMAT, porter.getChatId(), null);
                                }
                            }
                        } else {
                            answerService.savePorterAnswer(porter, message.getText());
                            Question question = questionService.getNextQuestionForPorter(porter);
                            if (question == null) {
                                porterService.setFinishAskingQuestions(porter);
                                scenarioForKnownPorter(messagesPackage, porter);
                            } else {
                                customSendMessage(messagesPackage, question.getText(), porter.getChatId(), null);
                                if (question.getLabel().equals("TIMETABLE")) {
                                    porterService.setIsTimetable(porter, true);
                                    customSendMessage(messagesPackage, BotModel.InlineButtons.Texts.PORTER_SELECT_TIMETABLE, porter.getChatId(), BotModel.InlineKeyboards.PORTER_TIMETABLE_ACTION_KEYBOARD);
                                }
                            }
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
        Order order = orderService.findOrderByCustomerAndStatus(customer, Status.PROCESSING);
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
            customerService.setOrderSearchingProcessing(customer, false);
            Order createdOrder = orderService.setStatusToOrderByCustomer(customer, Status.PROCESSING, Status.SEARCHING);
            customSendMessage(messagesPackage, BotModel.Messages.ORDER_CREATION_FINISHED, customer.getChatId(), BotModel.InlineKeyboards.SELECT_CUSTOMER_ACTION_KEYBOARD);
            sendNotificationForPorters(messagesPackage, createdOrder);
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
                callBackSelectPorterHandler(messagesPackage, callbackQuery.getFrom(), callbackQuery.getMessage().getChatId());
                break;
            }
            case BotModel.InlineButtons.Commands.SELECT_CUSTOMER_CMD: {
                callBackSelectCustomerHandler(messagesPackage, callbackQuery.getFrom(), callbackQuery.getMessage().getChatId());
                break;
            }
            case BotModel.InlineButtons.Commands.CUSTOMER_MAKE_ORDER_CMD: {
                callBackCustomerMakeOrderHandler(messagesPackage, (Customer) botUser);
                break;
            }
            default: {
                Porter porter = (Porter) botUser;
                Integer orderId = getOrderIdFromPorterOrderExecutionCommand(command);
                if (orderId != -1) {
                    try {
                        Order order = orderService.subscribePorterForOrderAndReturnOrder(orderId, porter);
                        if (order.getStatus() == Status.RECRUITMENT_COMPLETED) {
                            List<Porter> porters = order.getPorters();
                            Customer customer = order.getCustomer();
                            orderRecruitmentCompletedHandler(messagesPackage, porters, customer, orderId);
                        }
                    } catch (CustomBotException cbe) {
                        String notification = "";
                        switch (cbe.getErrorCode()) {
                            case BotModel.ErrorHandling.ErrorCodes.EY_0001: {
                                notification = BotModel.Notifications.UNFORTUNATELY_ALL_WORKERS_WERE_FOUND;
                                break;
                            }
                            case BotModel.ErrorHandling.ErrorCodes.EY_0002: {
                                notification = String.format(BotModel.Notifications.PORTER_HAD_CHOSEN_CURRENT_ORDER, orderId);
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                        customSendMessage(messagesPackage, notification, porter.getChatId(), null);
                    }
                    break;
                }
                Integer dayId = getOrderIdFromPorterDayForTimetableCommand(command);
                if (dayId != -1) {
                    porterService.setEditingDayTimetable(porter, dayId);
                    customSendMessage(messagesPackage, BotModel.Notifications.INPUT_TIME_START, porter.getChatId(), null);
                    break;
                }
                break;
            }
        }
    }

    private void orderRecruitmentCompletedHandler(MessagesPackage messagesPackage, List<Porter> porters, Customer customer, Integer orderId) {
        for (Porter porter : porters) {
            customSendMessage(messagesPackage, String.format(BotModel.Notifications.ORDER_RECRUITMENT_COMPLETED_FOR_PORTERS, orderId), porter.getChatId(), null);
        }
        customSendMessage(messagesPackage, String.format(BotModel.Notifications.ORDER_RECRUITMENT_COMPLETED_FOR_CUSTOMER, orderId), customer.getChatId(), null);
    }

    private Integer getOrderIdFromPorterOrderExecutionCommand(String command) {
        try {
            String orderIdStr = command.replaceFirst(BotModel.InlineButtons.Commands.PORTER_EXECUTE_ORDER_REGEX, "");
            return Integer.parseInt(orderIdStr);
        } catch (PatternSyntaxException | NumberFormatException e) {
            return -1;
        }
    }

    private Integer getOrderIdFromPorterDayForTimetableCommand(String command) {
        String dayAndOrderIdStr = "";
        Pattern pattern = Pattern.compile(BotModel.InlineButtons.Commands.DAY_SELECT_TIMETABLE_REGEX);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            dayAndOrderIdStr = matcher.replaceAll("");
        }
        return StringUtils.isEmpty(dayAndOrderIdStr) ? -1 : Integer.parseInt(dayAndOrderIdStr);
    }

    private void callBackCustomerMakeOrderHandler(MessagesPackage messagesPackage, Customer customer) {
        customerService.setOrderSearchingProcessing(customer, true);
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

    private void sendNotificationForPorters(MessagesPackage messagesPackage, Order order) {
        String description = String.format(BotModel.InlineButtons.Texts.ORDER_NOTIFICATION_TEMPLATE,
                order.getOrderDate(), order.getWorkersNum(), order.getHoursNum(), order.getPrice());
        List<Porter> porters = porterService.findPortersByTimetable();
        for (Porter porter : porters) {
            customSendMessage(messagesPackage, description, porter.getChatId(), getKeyBoardOfExecutingOrderForPorter(order.getId()));
        }
    }

    private InlineKeyboardMarkup getKeyBoardOfExecutingOrderForPorter(Integer orderId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(BotModel.InlineButtons.Texts.PORTER_WANTS_TO_EXECUTE_ORDER);
        inlineKeyboardButton.setCallbackData(String.format(BotModel.InlineButtons.Commands.PORTER_EXECUTE_ORDER, orderId));
        inlineKeyboardMarkup.setKeyboard(ImmutableList.of(ImmutableList.of(inlineKeyboardButton)));
        return inlineKeyboardMarkup;
    }
}
