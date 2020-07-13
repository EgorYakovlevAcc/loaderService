package com.service.impl;

import com.model.user.BotUser;
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
}
