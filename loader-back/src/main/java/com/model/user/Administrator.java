package com.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "administrators")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Administrator implements BotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fullName;
    private Integer telegramId;
    private Long chatId;
}
