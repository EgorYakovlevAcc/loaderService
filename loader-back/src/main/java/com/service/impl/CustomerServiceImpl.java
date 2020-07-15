package com.service.impl;

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
        customerRepository.deleteById(customerId);
    }
}
