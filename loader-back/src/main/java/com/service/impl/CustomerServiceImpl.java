package com.service.impl;

import com.model.order.Order;
import com.model.order.Status;
import com.model.user.Customer;
import com.model.user.Porter;
import com.repo.CustomerRepository;
import com.repo.OrderRepository;
import com.service.CustomerService;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderService orderService;

    @Override
    public Customer findCustomerByTelegramId(Integer id) {
        return customerRepository.findCustomerByTelegramId(id);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void setFinishAskingQuestions(Customer customer) {
        customer.setAskingQuestions(false);
        customer.setFinishedAskingQuestions(true);
    }

    @Override
    public void setOrderSearchingProcessing(Customer customer, boolean b) {
        //turn off order processing creation action
        if (!b) {
            customer.setOrderQuestionNum(0);
        }
        customer.setOrderCreationProcessing(b);
        customerRepository.save(customer);
    }

    @Override
    public void updateOrderCreationQuestionNum(Customer customer, int i) {
        customer.setOrderQuestionNum(i);
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        orderService.deleteOrders(customer.getOrders());
        customerRepository.deleteById(customerId);
    }

    @Override
    public void setEmail(Customer customer, String email) {
        customer.setEmail(email);
        customer.setEmailInput(false);
        customerRepository.save(customer);
    }

    @Override
    public void setStartEmailInput(Customer customer) {
        customer.setEmailInput(true);
        customerRepository.save(customer);
    }

    @Override
    public void fullDeleteCustomer(Customer customer) {
        customer.setOrders(null);
        customerRepository.save(customer);
        customerRepository.delete(customer);
    }

    @Override
    public void setMpnForCustomer(Customer botUser, String mpn) {
        botUser.setMpn(mpn);
        customerRepository.save(botUser);
    }
}
