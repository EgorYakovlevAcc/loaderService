package com.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    public Integer telegramId;
    public Long chatId;
}
