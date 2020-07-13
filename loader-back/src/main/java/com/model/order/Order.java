package com.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "workers_num")
    private Integer workersNum;
    private Double price;
    @Column(name = "hours_num")
    private Integer hoursNum;
    private Status tatus;
}
