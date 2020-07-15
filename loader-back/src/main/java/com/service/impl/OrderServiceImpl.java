package com.service.impl;

import com.bot.Bot;
import com.bot.BotModel;
import com.bot.MessagesPackage;
import com.exception.CustomBotException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.model.order.Order;
import com.model.order.Status;
import com.model.user.Customer;
import com.model.user.Porter;
import com.repo.OrderRepository;
import com.service.CustomerService;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;

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
        Integer numOfWorkers = StringUtils.isEmpty(answer) ? BotModel.DefaultValues.AMOUNT_OF_PORTERS : Integer.parseInt(answer);
        order.setWorkersNum(numOfWorkers);
        order.setRestAmountOfWorkers(numOfWorkers);
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

    @Override
    public Order findOrderById(Integer id) {
        return orderRepository.findOrderById(id);
    }

    @Override
    public Order subscribePorterForOrderAndReturnOrder(Integer orderId, Porter porter) {
        Order order = orderRepository.findOrderById(orderId);
        //todo: поддержка других статусов
        if (order.getStatus() != Status.SEARCHING) {
            throw new CustomBotException(BotModel.ErrorHandling.ErrorCodes.EY_0001,
                    BotModel.ErrorHandling.ErrorName.EY_0001,
                    BotModel.ErrorHandling.ErrorDescription.EY_0001);
        }
        List list = order.getPorters();
        list.add(porter);
        order.setPorters(list);
        Integer restAmountOfWorkers = order.getRestAmountOfWorkers();
        order.setRestAmountOfWorkers(restAmountOfWorkers - 1);
        if (restAmountOfWorkers - 1 <= 0) {
            order.setStatus(Status.RECRUITMENT_COMPLETED);
            //todo: подумать над транзакционностью
        }
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
