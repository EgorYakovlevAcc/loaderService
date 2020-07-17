package com.service.impl;

import com.model.order.Order;
import com.model.user.Porter;
import com.repo.PorterRepository;
import com.service.PorterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PorterServiceImpl implements PorterService {
    @Autowired
    private PorterRepository porterRepository;

    @Override
    public Porter findPorterByTelegramId(Integer id) {
        return porterRepository.findPorterByTelegramId(id);
    }

    @Override
    public Porter createPorter(Porter porter) {
        return porterRepository.save(porter);
    }

    @Override
    public List<Porter> findPortersByTimetable() {
        return porterRepository.findAll();
    }

    @Override
    public void setFinishAskingQuestions(Porter porter) {
        porter.setFinishedAskingQuestions(true);
        porter.setAskingQuestions(false);
        porterRepository.save(porter);
    }

    @Override
    public List<Porter> findAll() {
        return porterRepository.findAll();
    }

    @Override
    public void deletePorterById(Integer porterId) {
        Porter porter = porterRepository.findPorterById(porterId);
        List<Order> orders = porter.getOrders();
        porter.setOrders(null);
        porterRepository.save(porter);
        porterRepository.deleteById(porterId);
    }

    @Override
    public void setIsTimetable(Porter porter, boolean b) {
        porter.setStartTimetable(b);
        porterRepository.save(porter);
    }
}
