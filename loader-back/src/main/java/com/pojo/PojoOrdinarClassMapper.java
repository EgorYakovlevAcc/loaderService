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
        }
        else {
            questionPojo.setUserType("PORTER");
        }
        return questionPojo;
    }

    public static List<Question> questionsToQuestionPojosMapping(List<com.model.Question> questionList) {
        return questionList.stream()
                .map(PojoOrdinarClassMapper::questionToQuestionPojoMapping)
                .collect(Collectors.toList());
    }
}
