package com.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    public Integer telegramId;
    public Long chatId;
}
