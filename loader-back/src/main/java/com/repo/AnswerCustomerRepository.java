package com.repo;

import com.model.answer.AnswerCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerCustomerRepository extends JpaRepository<AnswerCustomer, Integer> {

}
