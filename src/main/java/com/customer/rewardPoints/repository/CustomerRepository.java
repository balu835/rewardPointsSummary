package com.customer.rewardPoints.repository;

import com.customer.rewardPoints.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    CustomerEntity findByCustomerId(long customerId);
}
