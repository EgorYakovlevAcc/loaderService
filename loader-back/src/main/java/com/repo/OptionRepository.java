package com.repo;

import com.model.Option;
import com.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Integer> {
    Option findOptionById(Integer id);
    List<Option> findOptionByQuestion(Question question);
}
