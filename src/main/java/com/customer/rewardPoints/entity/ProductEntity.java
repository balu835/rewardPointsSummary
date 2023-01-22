package com.customer.rewardPoints.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Table(name = "DT_PRDT")
@Data
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false, unique = true)
    private String productType;

    @Column(nullable = false)
    private BigDecimal productPrice;

    @Column(nullable = false)
    private Date effectiveDate;

    @Column(nullable = false)
    private Date endDate;

}
