package com.service;

import com.model.user.Anonymous;

public interface AnonymousService {
    void save(Anonymous anonymous);
    void delete(Anonymous anonymous);

    void createAnonymous(Integer id, Long chatId);

    Anonymous findAnonymousByTelegramId(Integer telegramId);

    void addEmailToAnonymous(Anonymous anonymous, String email);
}
