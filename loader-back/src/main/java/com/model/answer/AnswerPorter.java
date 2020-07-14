package com.model.answer;

import com.model.Question;
import com.model.user.Porter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "answers_porter")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerPorter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String content;
    private Integer questionNum;
    @OneToOne(mappedBy="answer", cascade = CascadeType.ALL)
    private Porter porter;
}
