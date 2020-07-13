package com.repo;

import com.model.Question;
import com.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findQuestionById(Integer id);
    List<Question> findQuestionsByUserType(UserType userType);
}
