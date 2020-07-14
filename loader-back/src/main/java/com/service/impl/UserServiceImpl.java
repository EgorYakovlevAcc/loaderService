package com.service.impl;

import com.model.user.BotUser;
import com.model.user.Customer;
import com.model.user.Porter;
import com.service.CustomerService;
import com.service.PorterService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PorterService porterService;
    @Autowired
    private CustomerService customerService;

    @Override
    public BotUser findTelegramUserByTelegramId(Integer id) {
        BotUser telegramUser = porterService.findPorterByTelegramId(id);
        return telegramUser != null ? telegramUser : customerService.findCustomerByTelegramId(id);
    }

    @Override
    public Porter createPorter(Integer telegramUserId, Long chatId) {
        Porter porter = new Porter();
        porter.setChatId(chatId);
        porter.setFinishedAskingQuestions(false);
        porter.setAskingQuestions(false);
        porter.setTelegramId(telegramUserId);
        porter.setCurrentQuestionNum(-1);
        return porterService.createPorter(porter);
    }

    @Override
    public Customer createCustomer(Integer telegramUserId, Long chatId) {
        Customer customer = new Customer();
        customer.setFinishedAskingQuestions(false);
        customer.setAskingQuestions(false);
        customer.setChatId(chatId);
        customer.setTelegramId(telegramUserId);
        customer.setCurrentQuestionNum(-1);
        return customerService.createCustomer(customer);
    }
}
