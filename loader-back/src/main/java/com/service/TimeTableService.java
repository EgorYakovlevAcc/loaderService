package com.service;

import com.model.TimeTable;
import com.model.user.Porter;

import java.sql.Time;

public interface TimeTableService {
    TimeTable createTimeTableByDayAndPorter(Integer dayId, Porter porter);
    void completeDayTimetableByStartTime(Porter porter, Time start);
    TimeTable completeDayTimetableByFinishTime(Porter porter, Time finish);

    String getTimetableDescription(Porter porter);
}
