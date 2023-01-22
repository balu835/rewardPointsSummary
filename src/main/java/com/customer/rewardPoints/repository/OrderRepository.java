package com.customer.rewardPoints.repository;

import com.customer.rewardPoints.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerCustomerId(long customerId);

    List<OrderEntity> findByCustomerCustomerIdAndOrderDateGreaterThanEqual(long customerId, Date orderDate);


}
