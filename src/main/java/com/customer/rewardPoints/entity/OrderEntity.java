package com.customer.rewardPoints.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "DT_ORDR")
@Data
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(nullable = true)
    private String comments;

    @Column(nullable = false)
    private BigDecimal orderAmount;

    @Column(nullable = false)
    private Date orderDate;

}
