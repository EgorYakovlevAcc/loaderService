package com.pojo;

import com.model.UserType;

import java.util.List;
import java.util.stream.Collectors;

public class PojoOrdinarClassMapper {
    public static Question questionToQuestionPojoMapping(com.model.Question question) {
        Question questionPojo = new Question();
        questionPojo.setContent(question.getText());
        questionPojo.setId(question.getId());
        if (question.getUserType() == UserType.CUSTOMER) {
            questionPojo.setUserType("CUSTOMER");
        } else {
            questionPojo.setUserType("PORTER");
        }
        return questionPojo;
    }

    public static List<Question> questionsToQuestionPojosMapping(List<com.model.Question> questionList) {
        return questionList.stream()
                .map(PojoOrdinarClassMapper::questionToQuestionPojoMapping)
                .collect(Collectors.toList());
    }

    public static Porter porterToPorterPojoMapping(com.model.user.Porter porter) {
        Porter porterPojo = new Porter();
        porterPojo.setPorterName(porter.getFullName());
        porterPojo.setTelegramId(porter.getTelegramId());
        porterPojo.setUserName(porter.getUsername());
        porterPojo.setId(porter.getId());
        return porterPojo;
    }

    public static List<Porter> portersToPorterPojosMapping(List<com.model.user.Porter> porters) {
        return porters.stream()
                .map(PojoOrdinarClassMapper::porterToPorterPojoMapping)
                .collect(Collectors.toList());
    }

    public static Customer customerToCustomerPojoMapping(com.model.user.Customer customer) {
        Customer customerPojo = new Customer();
        customerPojo.setCustomerName(customer.getName());
        customerPojo.setId(customer.getId());
        customerPojo.setUserName(customer.getUsername());
        customerPojo.setTelegramId(customer.getTelegramId());
        return customerPojo;
    }

    public static List<Customer> customersToCustomerPojosMapping(List<com.model.user.Customer> customers) {
        return customers.stream()
                .map(PojoOrdinarClassMapper::customerToCustomerPojoMapping)
                .collect(Collectors.toList());
    }
}
