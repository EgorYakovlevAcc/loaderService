package com.model;

import com.model.user.Porter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
@Setter
@Table(name="porters_timetable")
@AllArgsConstructor
@NoArgsConstructor
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer day;
    private Time start;
    private Time finish;
    private boolean isDayEditing;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "porter_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Porter porter;
}
