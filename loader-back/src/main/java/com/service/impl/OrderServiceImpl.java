package com.service.impl;

import com.bot.BotModel;
import com.model.order.Order;
import com.model.order.Status;
import com.model.user.Customer;
import com.repo.OrderRepository;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Customer customer) {
        Order order = new Order();
        order.setCustomer(customer);
        customer.setOrderQuestionNum(0);
        order.setStatus(Status.PROCESSING);
        return orderRepository.save(order);
    }

    @Override
    public Order findOrderByCustomerAndStatus(Customer customer, Status status) {
        return orderRepository.findOrderByCustomerAndStatus(customer, status);
    }

    @Override
    public void setPayForHourOfOnePersonforOrder(Order order, String answer) {
        order.setPrice(StringUtils.isEmpty(answer) ? BotModel.DefaultValues.PAY_FOR_AN_HOUR : Double.parseDouble(answer));
        orderRepository.save(order);
    }

    @Override
    public void setTimeForExecutingOrder(Order order, String answer) {
        order.setHoursNum(StringUtils.isEmpty(answer) ? BotModel.DefaultValues.ORDER_EXECUTING_TIME : Double.parseDouble(answer));
        orderRepository.save(order);
    }

    @Override
    public void setAmountOfPortersForOrder(Order order, String answer) {
        order.setWorkersNum(StringUtils.isEmpty(answer) ? BotModel.DefaultValues.AMOUNT_OF_PORTERS : Integer.parseInt(answer));
        orderRepository.save(order);
    }

    @Override
    public void setDateForOrder(Order order, String answer) {
        try {
            order.setOrderDate(StringUtils.isEmpty(answer) ? BotModel.DefaultValues.ORDER_EXECUTION_DATE : (new SimpleDateFormat("dd/MM/yyyy")).parse(answer));
            orderRepository.save(order);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order setStatusToOrderByCustomer(Customer customer, Status currentStatus, Status newStatus) {
        Order order = findOrderByCustomerAndStatusTemporary(customer, currentStatus);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    private Order findOrderByCustomerAndStatusTemporary(Customer customer, Status currentStatus) {
        //todo:refactoring must have!
        return orderRepository.findAll().stream()
                .filter(x -> x.getStatus() == currentStatus)
                .filter(x -> x.getCustomer().getId().equals(customer.getId()))
                .findFirst()
                .orElse(null);
    }

}
