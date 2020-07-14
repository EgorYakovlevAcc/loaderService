package com.service.impl;

import com.model.user.Porter;
import com.repo.PorterRepository;
import com.service.PorterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PorterServiceImpl implements PorterService {
    @Autowired
    private PorterRepository porterRepository;

    @Override
    public Porter findPorterByTelegramId(Integer id) {
        return null;
    }

    @Override
    public Porter createPorter(Porter porter) {
        return porterRepository.save(porter);
    }


}
