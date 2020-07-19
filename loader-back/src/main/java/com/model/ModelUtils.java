package com.model;

import com.model.order.Order;
import com.model.user.Porter;

import java.util.Calendar;

public class ModelUtils {
    public static boolean filterPortersByTimetable(Porter porter, Order order) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(order.getOrderDate());
        Integer orderDayId = calendar.get(Calendar.DAY_OF_WEEK);

        return porter.getTimeTables().stream()
                .filter(timeTable -> timeTable.getDay().equals(orderDayId))
                .anyMatch(timeTable -> (timeTable.getStart().before(order.getTime())) && (timeTable.getFinish().after(order.getTime())));
    }
}
