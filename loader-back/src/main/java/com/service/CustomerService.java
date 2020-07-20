package com.service;

import com.model.user.Customer;
import com.model.user.Porter;

import java.util.List;

public interface CustomerService {
    Customer findCustomerByTelegramId(Integer id);
    Customer findCustomerByEmail(String email);
    void save(Customer customer);

    Customer createCustomer(Customer customer);

    void setFinishAskingQuestions(Customer customer);

    void setOrderSearchingProcessing(Customer customer, boolean b);

    void updateOrderCreationQuestionNum(Customer customer, int i);

    List<Customer> findAll();

    void deleteCustomerById(Integer customerId);

    void setEmail(Customer customer, String email);
}
