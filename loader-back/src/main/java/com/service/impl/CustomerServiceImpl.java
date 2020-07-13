package com.service.impl;

import com.model.user.Customer;
import com.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public Customer findCustomerByTelegramId(Integer id) {
        return null;
    }
}
