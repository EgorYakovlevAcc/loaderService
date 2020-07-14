package com.service;

import com.model.Question;
import com.model.user.BotUser;
import com.model.user.Customer;
import com.model.user.Porter;

import java.util.List;

public interface QuestionService {
    Question findQuestionById(Integer id);
    List<Question> findAll();
    void save(Question question);
    void delete(Question question);
    Question getNextQuestionForPorter(Porter porter);
    Question getNextQuestionForCustomer(Customer customer);
    void deleteQuestionById(Integer id);
    void saveQuestionWithImageContent(Question question, byte[] image);
    List<Question> findAllQuestionsForPorters();
    List<Question> findAllQuestionsForCustomers();

    void createQuestionForTypeByPojo(com.pojo.Question question);
}
