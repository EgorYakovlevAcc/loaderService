package com.service.impl;

import com.model.Question;
import com.model.user.Porter;
import com.repo.PorterRepository;
import com.service.PorterService;
import com.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class PorterServiceImpl implements PorterService {
    @Override
    public Porter findPorterByTelegramId(Integer id) {
        return null;
    }


}
