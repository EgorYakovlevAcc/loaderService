package com.repo;

import com.model.TimeTable;
import com.model.user.Porter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {
    TimeTable findTimeTableByPorterAndIsDayEditing(Porter porter, boolean isDayEditing);
    List<TimeTable> findTimeTablesByPorterAndIsDayEditing(Porter porter, boolean isDayEditing);
    TimeTable findTimeTableByPorterAndDayAndIsDayEditing(Porter porter, Integer day, boolean isDayEditing);
    TimeTable findTimeTableByPorterAndAndIsDayEditing(Porter porter, boolean isDayEditing);
}
