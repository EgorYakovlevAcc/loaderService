package com.repo;

import com.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByTelegramId(Integer id);
    Customer findCustomerByEmail(String email);
    Customer findCustomerById(Integer customerId);
}
