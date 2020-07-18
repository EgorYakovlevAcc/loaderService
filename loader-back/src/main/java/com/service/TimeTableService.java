package com.service;

import com.model.TimeTable;
import com.model.user.Porter;

import java.sql.Time;

public interface TimeTableService {
    TimeTable createTimeTableByDayAndPorter(Integer dayId, Porter porter);
    void completeDayTimetable(Porter porter, Time start, Time finish);
}
