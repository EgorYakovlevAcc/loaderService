package com.service.impl;

import com.model.TimeTable;
import com.model.user.Porter;
import com.repo.TimeTableRepository;
import com.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;

@Service
public class TimeTableServiceImpl implements TimeTableService{
    @Autowired
    private TimeTableRepository timeTableRepository;

    @Override
    public TimeTable createTimeTableByDayAndPorter(Integer dayId, Porter porter) {
        TimeTable timeTable = new TimeTable();
        timeTable.setDayEditing(true);
        timeTable.setDay(dayId);
        timeTable.setPorter(porter);
        return timeTableRepository.save(timeTable);
    }

    @Override
    public void completeDayTimetable(Porter porter, Time start, Time finish) {
        TimeTable timeTable = timeTableRepository.findTimeTableByPorterAndIsDayEditing(porter, true);
        timeTable.setDayEditing(false);
        timeTable.setStart(start);
        timeTable.setStart(finish);
        timeTableRepository.save(timeTable);
    }
}
