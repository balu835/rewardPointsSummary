package com.customer.rewardPoints.service.domain;

import com.customer.rewardPoints.entity.CustomerEntity;
import com.customer.rewardPoints.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * To find customer for the given customer Id.
     *
     * @param customerId
     * @return
     */
    public CustomerEntity findCustomerDetails(long customerId) {
        return customerRepository.findByCustomerId(customerId);
    }
}
