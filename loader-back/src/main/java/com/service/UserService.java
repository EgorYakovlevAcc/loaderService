package com.service;

import com.model.user.BotUser;
import com.model.user.Customer;
import com.model.user.Porter;

public interface UserService {
    BotUser findTelegramUserByTelegramId(Integer id);
    Customer createCustomer(Integer telegramUserId, Long chatId);
    Porter createPorter(Integer telegramUserId, Long chatId);
}
