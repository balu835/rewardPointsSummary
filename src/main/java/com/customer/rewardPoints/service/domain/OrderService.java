package com.customer.rewardPoints.service.domain;

import com.customer.rewardPoints.entity.OrderEntity;
import com.customer.rewardPoints.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    /**
     * To retrieve all orders amounts where the order is placed after the order date
     * for the given customerId.
     *
     * @param customerId
     * @param orderDate
     * @return
     */
    public List<OrderEntity> fetchOrdersForAllPreviousOrdersFromOrderDate(long customerId, Date orderDate) {
        return orderRepository.findByCustomerCustomerIdAndOrderDateGreaterThanEqual(customerId, orderDate);
    }
}
