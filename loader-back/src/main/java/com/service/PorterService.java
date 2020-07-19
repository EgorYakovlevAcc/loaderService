package com.service;

import com.model.TimeTable;
import com.model.user.Porter;

import java.util.List;

public interface PorterService {
    Porter findPorterByTelegramId(Integer id);
    Porter createPorter(Porter porter);

    List<Porter> findPortersByTimetable();
    void setFinishAskingQuestions(Porter porter);

    List<Porter> findAll();

    void deletePorterById(Integer porterId);

    void setIsTimetable(Porter porter, boolean b);

    void setEditingDayTimetable(Porter porter, Integer dayId);

    void setHasStartDateInputOn(Porter porter, String startTime);

    TimeTable setHasStartDateInputOff(Porter porter, String finishTime);

    void setEditingDayTimetableOff(Porter porter, Integer dayId);

    void updateTimeTableForPorter(Porter porter);

    void setHasTimetableChanged(Porter porter, boolean b);
}
