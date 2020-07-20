package com.service.impl;

import com.bot.Bot;
import com.bot.BotModel;
import com.exception.CustomBotException;
import com.model.TimeTable;
import com.model.order.Order;
import com.model.user.Porter;
import com.repo.PorterRepository;
import com.service.PorterService;
import com.service.TimeTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PorterServiceImpl implements PorterService {
    @Autowired
    private TimeTableService timeTableService;
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
        porter.setOrders(null);
        porterRepository.save(porter);
        porterRepository.deleteById(porterId);
    }

    @Override
    public void setIsTimetable(Porter porter, boolean b) {
        porter.setStartTimetable(b);
        porterRepository.save(porter);
    }

    @Override
    public void setEditingDayTimetable(Porter porter, Integer dayId) {
        porter.setStartTimetable(true);
        porterRepository.save(porter);
    }

    @Override
    public void setHasStartDateInputOn(Porter porter, String startTimeStr)  throws CustomBotException{
        porter.setHasStartDateInput(true);
        Time startTime = getTimeByStr(startTimeStr);
        timeTableService.completeDayTimetableByStartTime(porter, startTime);
        porterRepository.save(porter);
    }

    @Override
    public TimeTable setHasStartDateInputOff(Porter porter, String finishTimeStr) throws CustomBotException {
        porter.setHasStartDateInput(false);
        Time finishTime = getTimeByStr(finishTimeStr);
        TimeTable timeTable = timeTableService.completeDayTimetableByFinishTime(porter, finishTime);
        porterRepository.save(porter);
        return timeTable;
    }

    private Time getTimeByStr(String timeStr) {
        Time time = getTimeByStrResult(timeStr);
        if (time == null) {
            throw new CustomBotException(BotModel.ErrorHandling.ErrorCodes.EY_0003,
                    BotModel.ErrorHandling.ErrorName.EY_0003,
                    BotModel.ErrorHandling.ErrorDescription.EY_0003);
        }
        return time;
    }

    private Time getTimeByStrResult(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Time time = new Time (sdf.parse(timeStr).getTime());
            return time;
        } catch (ParseException e) {
           return null;
        }
    }

    @Override
    public void setEditingDayTimetableOff(Porter porter, Integer dayId) {
        porter.setStartTimetable(false);
        porter.setHasChangeTimetable(false);
        porterRepository.save(porter);
    }

    @Override
    public void updateTimeTableForPorter(Porter porter) {
        TimeTable timeTable = timeTableService.findTimeTableByPorterAndIsDayEditing(porter);
    }

    @Override
    public void setHasTimetableChanged(Porter porter, boolean b) {
        porter.setHasChangeTimetable(b);
        porterRepository.save(porter);
    }

    @Override
    public Porter findPorterByEmail(String email) {
        return porterRepository.findPorterByEmail(email);
    }

    @Override
    public void setStartEmailInput(Porter porter) {
        porter.setEmailInput(true);
        porterRepository.save(porter);
    }

    @Override
    public void setEmail(Porter porter, String email) {
        porter.setEmailInput(false);
        porter.setEmail(email);
        porterRepository.save(porter);
    }

    @Override
    public void fullDeletePorter(Porter porter) {
        porter.setOrders(null);
        timeTableService.deleteAllTimeTableForPorter(porter);
        porterRepository.save(porter);
        porterRepository.delete(porter);
    }
}
