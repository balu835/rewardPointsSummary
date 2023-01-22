package com.customer.rewardPoints.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "DT_PRDT_ORDR")
@Data
public class ProductOrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private OrderEntity order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal amount;

}
