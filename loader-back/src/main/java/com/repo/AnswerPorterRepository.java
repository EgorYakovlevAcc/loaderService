package com.repo;

import com.model.answer.AnswerPorter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerPorterRepository extends JpaRepository<AnswerPorter, Integer> {

}
