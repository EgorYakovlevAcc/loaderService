package com.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.model.order.Order;
import com.model.user.Porter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Map;

public class ModelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelUtils.class);
    private static final Map<Integer, Integer> DAYS_MAP =ImmutableMap.<Integer, Integer>builder()
            .put(1, 6)
            .put(2, 0)
            .put(3, 1)
            .put(4, 2)
            .put(5, 3)
            .put(6, 4)
            .put(7, 5)
            .build();
    public static boolean filterPortersByTimetable(Porter porter, Order order) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(order.getOrderDate());
        Integer orderDayId = calendar.get(Calendar.DAY_OF_WEEK);
        orderDayId = DAYS_MAP.get(orderDayId);
        LOGGER.info("filterPortersByTimetable: orderDayId = {}", orderDayId);

        Integer finalOrderDayId = orderDayId;
        boolean result =  porter.getTimeTables().stream()
                .filter(timeTable -> timeTable.getDay().equals(finalOrderDayId))
                .anyMatch(timeTable -> (timeTable.getStart().before(order.getTime())) && (timeTable.getFinish().after(order.getTime())));
        LOGGER.info("filterPortersByTimetable: result = {}", result);
        return result;
    }
}
