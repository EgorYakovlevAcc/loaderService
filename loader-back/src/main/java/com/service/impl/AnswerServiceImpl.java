package com.service.impl;

import com.model.answer.AnswerCustomer;
import com.model.answer.AnswerPorter;
import com.model.user.Customer;
import com.model.user.Porter;
import com.repo.AnswerCustomerRepository;
import com.repo.AnswerPorterRepository;
import com.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerCustomerRepository answerCustomerRepository;
    @Autowired
    private AnswerPorterRepository answerPorterRepository;

    @Override
    public void saveCustomerAnswer(Customer customer, String content) {
        AnswerCustomer answer = new AnswerCustomer();
        answer.setContent(content);
        answer.setCustomer(customer);
        answer.setQuestionNum(customer.getCurrentQuestionNum());
        answerCustomerRepository.save(answer);
    }

    @Override
    public void savePorterAnswer(Porter porter, String content) {
        AnswerPorter answer = new AnswerPorter();
        answer.setContent(content);
        answer.setPorter(porter);
        answer.setQuestionNum(porter.getCurrentQuestionNum());
        answerPorterRepository.save(answer);
    }
}
