package com.model.user;

import com.model.TimeTable;
import com.model.answer.AnswerPorter;
import com.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "porters")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Porter implements BotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fullName;
    private String username;
    private String nationality;
    private String timetable;
    private Integer telegramId;
    private Integer currentQuestionNum;
    private Long chatId;
    private boolean isAskingQuestions;
    private boolean isFinishedAskingQuestions;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="porter_id")
    private AnswerPorter answer;
    private boolean isStartTimetable;
    private boolean hasStartDateInput;
    private String email;
    @ManyToMany
    private List<Order> orders;
    @OneToMany(mappedBy = "porter",cascade = CascadeType.ALL)
    private List<TimeTable> timeTables;
    private boolean hasChangeTimetable;
    private boolean isEmailInput;
}
