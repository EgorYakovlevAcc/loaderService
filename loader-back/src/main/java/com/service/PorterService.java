package com.service;

import com.model.user.Porter;

import java.util.List;

public interface PorterService {
    Porter findPorterByTelegramId(Integer id);
    Porter createPorter(Porter porter);

    List<Porter> findPortersByTimetable();
    void setFinishAskingQuestions(Porter porter);

    List<Porter> findAll();

    void deletePorterById(Integer porterId);
}
