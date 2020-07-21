package com.service.impl;

import com.model.user.BotUser;
import com.model.user.Customer;
import com.model.user.Porter;
import com.service.AdministratorService;
import com.service.CustomerService;
import com.service.PorterService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.User;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PorterService porterService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AdministratorService administratorService;

    @Override
    public BotUser findTelegramUserByTelegramId(Integer id) {
        BotUser telegramUser = porterService.findPorterByTelegramId(id);
        return telegramUser != null ? telegramUser : customerService.findCustomerByTelegramId(id);
    }

    @Override
    public BotUser findTelegramUserByEmail(String email) {
        return porterService.findPorterByEmail(email);
    }

    @Override
    public Porter createPorter(User user, Long chatId) {
        Porter porter = new Porter();
        porter.setFirstName(user.getFirstName());
        porter.setLastName(user.getLastName());
        porter.setChatId(chatId);
        porter.setFinishedAskingQuestions(false);
        porter.setAskingQuestions(false);
        porter.setTelegramId(user.getId());
        porter.setFullName(user.getUserName());
        porter.setStartTimetable(false);
        porter.setHasStartDateInput(false);
        porter.setCurrentQuestionNum(-1);
        return porterService.createPorter(porter);
    }

    @Override
    public Customer createCustomer(User user, Long chatId) {
        Customer customer = new Customer();
        customer.setFinishedAskingQuestions(false);
        customer.setAskingQuestions(false);
        customer.setChatId(chatId);
        customer.setFirstName(user.getFirstName());
        customer.setLastName(user.getLastName());
        customer.setName(user.getUserName());
        customer.setTelegramId(user.getId());
        customer.setCurrentQuestionNum(-1);
        return customerService.createCustomer(customer);
    }
}
