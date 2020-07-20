package com.model;

import com.model.order.Order;
import com.model.user.Porter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

public class ModelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelUtils.class);
    public static boolean filterPortersByTimetable(Porter porter, Order order) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(order.getOrderDate());
        Integer orderDayId = calendar.get(Calendar.DAY_OF_WEEK);
        LOGGER.info("filterPortersByTimetable: orderDayId = {}", orderDayId);

        boolean result =  porter.getTimeTables().stream()
                .filter(timeTable -> timeTable.getDay().equals(orderDayId))
                .anyMatch(timeTable -> (timeTable.getStart().before(order.getTime())) && (timeTable.getFinish().after(order.getTime())));
        LOGGER.info("filterPortersByTimetable: result = {}", result);
        return result;
    }
}
