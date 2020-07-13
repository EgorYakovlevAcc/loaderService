package com.service;

import com.model.user.BotUser;

public interface UserService {
    BotUser findTelegramUserByTelegramId(Integer id);
}
