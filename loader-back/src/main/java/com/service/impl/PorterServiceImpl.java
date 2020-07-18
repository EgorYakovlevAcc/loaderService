package com.service.impl;

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
import java.util.List;

@Service
@Transactional
public class PorterServiceImpl implements PorterService {
    @Autowired
    private TimeTableService timeTableService;
    @Autowired
    private PorterRepository porterRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(PorterServiceImpl.class);

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
        timeTableService.createTimeTableByDayAndPorter(dayId, porter);
        porterRepository.save(porter);
    }

    @Override
    public void setHasStartDateInputOn(Porter porter, String startTimeStr) {
        porter.setHasStartDateInput(true);
        LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!EGORKA = {}", startTimeStr);
        Time startTime = getTimeByStr(startTimeStr);
        timeTableService.completeDayTimetableByStartTime(porter, startTime);
        porterRepository.save(porter);
    }

    @Override
    public TimeTable setHasStartDateInputOff(Porter porter, String finishTimeStr) {
        porter.setHasStartDateInput(false);
        Time finishTime = getTimeByStr(finishTimeStr);
        TimeTable timeTable = timeTableService.completeDayTimetableByFinishTime(porter, finishTime);
        porterRepository.save(porter);
        return timeTable;
    }

    private Time getTimeByStr(String timeStr) {
        return Time.valueOf(timeStr);
    }
}
