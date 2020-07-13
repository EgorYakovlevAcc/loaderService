package com.service;

import com.model.Option;
import com.model.Question;

import java.util.List;

public interface OptionService {
    Option findOptionById(Integer id);
    List<Option> findOptionByQuestion(Question question);
    void createOptionsByQuestionAndContents(Integer questionId, List<com.pojo.Option> contents);
    Integer getCorrectIndexOfOptionByAnswer(String answerStr);
    void editOptionsByQuestionAndContents(Integer questionId, List<Option> previousOptions, List<com.pojo.Option> options);
    void delete(Option option);
    com.pojo.Option convertOptionModelToOptionPojo(Option option);
}
