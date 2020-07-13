package com.service;

import com.model.user.Customer;

public interface CustomerService {
    Customer findCustomerByTelegramId(Integer id);
}
