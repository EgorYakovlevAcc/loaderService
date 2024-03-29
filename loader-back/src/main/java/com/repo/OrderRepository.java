package com.repo;

import com.model.order.Order;
import com.model.order.Status;
import com.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Order, Integer> {
    Order findOrderByCustomer_IdAndStatus(Integer customer_Id, Status status);

    Order findOrderByCustomerAndStatus(Customer customer, Status status);

    Order findOrderById(Integer id);
}
