package com.service.impl;

import com.bot.BotModel;
import com.model.TimeTable;
import com.model.user.Porter;
import com.repo.TimeTableRepository;
import com.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

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
    public void completeDayTimetableByStartTime(Porter porter, Time start) {
        TimeTable timeTable = timeTableRepository.findTimeTableByPorterAndIsDayEditing(porter, true);
        timeTable.setStart(start);
        timeTableRepository.save(timeTable);
    }

    @Override
    public TimeTable completeDayTimetableByFinishTime(Porter porter, Time finish) {
        TimeTable timeTable = timeTableRepository.findTimeTableByPorterAndIsDayEditing(porter, true);
        timeTable.setDayEditing(false);
        timeTable.setFinish(finish);
        return timeTableRepository.save(timeTable);
    }

    @Override
    public String getTimetableDescription(Porter porter) {
        List<TimeTable> timeTableList = timeTableRepository.findTimeTablesByPorterAndIsDayEditing(porter, false);
        StringBuilder timetableDescription = new StringBuilder();
        for (TimeTable timeTable: timeTableList) {
            timetableDescription.append(BotModel.InlineButtons.Texts.Days.DAY_ID_AND_DAY.get(timeTable.getDay()) + ":\n" + "начало работы: " + timeTable.getStart() + "\n" + "Окончание рабочего: " + timeTable.getFinish() + "\n");
        }
        return timetableDescription.toString();
    }

    @Override
    public TimeTable findTimetableByPorterAndDayId(Porter porter, Integer dayId) {
        return timeTableRepository.findTimeTableByPorterAndDay(porter, dayId);
    }

    @Override
    public TimeTable findTimeTableByPorterAndIsDayEditing(Porter porter) {
        return timeTableRepository.findTimeTableByPorterAndAndIsDayEditing(porter, true);
    }
}
