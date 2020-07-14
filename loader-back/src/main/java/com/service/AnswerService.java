package com.service;

import com.model.user.Customer;
import com.model.user.Porter;

public interface AnswerService {
    void saveCustomerAnswer(Customer customer, String content);
    void savePorterAnswer(Porter porter, String content);
}
