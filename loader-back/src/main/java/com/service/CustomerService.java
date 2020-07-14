package com.service;

import com.model.user.Customer;

public interface CustomerService {
    Customer findCustomerByTelegramId(Integer id);
    void save(Customer customer);

    Customer createCustomer(Customer customer);

    void setFinishAskingQuestions(Customer customer);

    void setOrderSearchingProcessing(Customer customer, boolean b);

    void updateOrderCreationQuestionNum(Customer customer, int i);
}
