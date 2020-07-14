package com.service;

import com.model.order.Order;
import com.model.order.Status;
import com.model.user.Customer;

import java.util.List;

public interface OrderService {
    Order createOrder(Customer customer);
    Order findOrderByCustomerAndStatus(Customer customer, Status status);

    void setPayForHourOfOnePersonforOrder(Order order, String answer);

    void setTimeForExecutingOrder(Order order, String answer);

    void setAmountOfPortersForOrder(Order order, String answer);

    void setDateForOrder(Order order, String answer);

    Order setStatusToOrderByCustomer(Customer customer, Status currentStatus, Status newStatus);
}
