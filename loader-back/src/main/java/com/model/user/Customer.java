package com.model.user;

import com.model.answer.AnswerCustomer;
import com.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
    private boolean isOrderCreationProcessing;
    private Integer orderQuestionNum;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private AnswerCustomer answer;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Order> orders;
}
