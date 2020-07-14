package com.service;

import com.model.user.Porter;

public interface PorterService {
    Porter findPorterByTelegramId(Integer id);
    Porter createPorter(Porter porter);
  }
