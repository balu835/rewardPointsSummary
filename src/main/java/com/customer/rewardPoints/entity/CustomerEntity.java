package com.customer.rewardPoints.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DT_CUST")
@Data
public class CustomerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long customerId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false, unique = true)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private Date effectiveDate;

    @Column(nullable = true)
    private Date endDate;



}
