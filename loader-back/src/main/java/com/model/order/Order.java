package com.model.order;

import com.model.user.Customer;
import com.model.user.Porter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private Double hoursNum;
    private Status status;
    @ManyToOne(fetch =  FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToMany
    private List<Porter> porters;
}
