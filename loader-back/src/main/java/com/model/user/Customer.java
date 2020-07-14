package com.model.user;

import com.model.answer.AnswerCustomer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Customer implements BotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer telegramId;
    private Long chatId;
    private Integer currentQuestionNum;
    private boolean isAskingQuestions;
    private boolean isFinishedAskingQuestions;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private AnswerCustomer answer;
}
