package com.service;

import com.model.user.Customer;

public interface CustomerService {
    Customer findCustomerByTelegramId(Integer id);
    void save(Customer customer);

    Customer createCustomer(Customer customer);

    void setFinishAskingQuestions(Customer customer);
}
