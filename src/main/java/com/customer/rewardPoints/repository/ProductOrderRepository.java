package com.customer.rewardPoints.repository;

import com.customer.rewardPoints.entity.ProductOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrderEntity, Long> {
}
