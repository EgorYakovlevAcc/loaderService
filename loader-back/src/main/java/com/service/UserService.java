package com.service;

import com.model.user.BotUser;
import com.model.user.Customer;
import com.model.user.Porter;
import org.telegram.telegrambots.api.objects.User;

public interface UserService {
    BotUser findTelegramUserByTelegramId(Integer id);
    BotUser findTelegramUserByEmail(String email);
    Customer createCustomer(User user, Long chatId);
    Porter createPorter(User user, Long chatId);
}
