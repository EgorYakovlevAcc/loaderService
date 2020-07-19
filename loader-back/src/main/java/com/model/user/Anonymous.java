package com.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "anonymous")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Anonymous {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer telegramId;
    private Long chatId;
    private boolean forgetPassword;
    private String email;
}
