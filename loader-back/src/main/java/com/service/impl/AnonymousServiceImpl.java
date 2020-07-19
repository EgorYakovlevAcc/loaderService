package com.service.impl;

import com.bot.BotModel;
import com.exception.CustomBotException;
import com.model.user.Anonymous;
import com.repo.AnonymousRepository;
import com.service.AnonymousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnonymousServiceImpl implements AnonymousService {
    @Autowired
    private AnonymousRepository anonymousRepository;

    @Override
    public void save(Anonymous anonymous) {
        anonymousRepository.save(anonymous);
    }

    @Override
    public void delete(Anonymous anonymous) {
        anonymousRepository.delete(anonymous);
    }

    @Override
    public void createAnonymous(Integer telegramId, Long chatId) {
        Anonymous anonymous = new Anonymous();
        anonymous.setChatId(chatId);
        anonymous.setForgetPassword(true);
        anonymous.setTelegramId(telegramId);
        anonymousRepository.save(anonymous);
    }

    @Override
    public Anonymous findAnonymousByTelegramId(Integer telegramId) {
        return anonymousRepository.findAnonymousByTelegramId(telegramId);
    }

    @Override
    public void addEmailToAnonymous(Anonymous anonymous, String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z]*@[a-zA-Z]\\.(com)|(ru)|(org)");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            throw new CustomBotException(BotModel.ErrorHandling.ErrorCodes.EY_0004,
                    BotModel.ErrorHandling.ErrorName.EY_0004,
                    BotModel.ErrorHandling.ErrorDescription.EY_0004);
        }
        anonymous.setEmail(email);
        anonymousRepository.save(anonymous);
    }
}
