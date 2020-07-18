package com.repo;

import com.model.TimeTable;
import com.model.user.Porter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {
    TimeTable findTimeTableByPorterAndIsDayEditing(Porter porter, boolean isDayEditing);
}
