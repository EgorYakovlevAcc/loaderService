package com.service;

import com.model.TimeTable;
import com.model.user.Porter;

import java.sql.Time;

public interface TimeTableService {
    TimeTable createTimeTableByDayAndPorter(Integer dayId, Porter porter);
    void completeDayTimetableByStartTime(Porter porter, Time start);
    TimeTable completeDayTimetableByFinishTime(Porter porter, Time finish);

    String getTimetableDescription(Porter porter);

    TimeTable findTimetableByPorterAndDayIdAndIsDayEditing(Porter porter, Integer dayId, boolean isDayEditing);

    TimeTable findTimeTableByPorterAndIsDayEditing(Porter porter);

    void setDayIsEditing(TimeTable timeTable, boolean isEditing);

    void cancelEditingTimetable(Porter porter);

    void deleteAllTimeTableForPorter(Porter porter);
}
