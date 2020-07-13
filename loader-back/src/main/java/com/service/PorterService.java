package com.service;

import com.model.Question;
import com.model.user.Porter;
import org.telegram.telegrambots.api.objects.Message;

import java.util.List;

public interface PorterService {
    Porter findPorterByTelegramId(Integer id);
  }
