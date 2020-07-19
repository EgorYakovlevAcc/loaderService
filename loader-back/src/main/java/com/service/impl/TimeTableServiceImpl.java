package com.service.impl;

import com.bot.BotModel;
import com.model.TimeTable;
import com.model.user.Porter;
import com.repo.TimeTableRepository;
import com.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Comparator;
import java.util.List;

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
        timeTableList.sort(new Comparator<TimeTable>() {
            @Override
            public int compare(TimeTable o1, TimeTable o2) {
                return o1.getDay() - o2.getDay();
            }
        });
        StringBuilder timetableDescription = new StringBuilder();
        for (TimeTable timeTable: timeTableList) {
            timetableDescription.append(BotModel.InlineButtons.Texts.Days.DAY_ID_AND_DAY.get(timeTable.getDay()) + ":\n" + "начало работы: " + timeTable.getStart() + "\n" + "Окончание рабочего: " + timeTable.getFinish() + "\n");
        }
        return timetableDescription.toString();
    }

    @Override
    public TimeTable findTimetableByPorterAndDayIdAndIsDayEditing(Porter porter, Integer dayId, boolean isDayEditing) {
        return timeTableRepository.findTimeTableByPorterAndDayAndIsDayEditing(porter, dayId, isDayEditing);
    }

    @Override
    public TimeTable findTimeTableByPorterAndIsDayEditing(Porter porter) {
        return timeTableRepository.findTimeTableByPorterAndAndIsDayEditing(porter, true);
    }

    @Override
    public void setDayIsEditing(TimeTable timeTable) {
        timeTable.setDayEditing(true);
        timeTableRepository.save(timeTable);
    }
}
