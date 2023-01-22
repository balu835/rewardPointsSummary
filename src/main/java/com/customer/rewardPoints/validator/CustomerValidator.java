package com.customer.rewardPoints.validator;

import com.customer.rewardPoints.entity.CustomerEntity;
import com.customer.rewardPoints.exceptions.RewardPointsException;
import com.customer.rewardPoints.service.domain.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidator {

    public static final String RECEIVED_INVALID_CUSTOMER_ID_PLEASE_ENTER_A_VALID_USER_ID = "Received Invalid customerId. Please enter a valid customerId.";
    public static final String CUSTOMER_DOES_NOT_EXISTS_PLEASE_TRY_A_DIFFERENT_CUSTOMER = "Customer does not exists. Please try a different customer.";


    @Autowired
    CustomerService customerService;

    public boolean isValidCustomer(Long customerId) {
        if( customerId <= 0) {
            throw new RewardPointsException(RECEIVED_INVALID_CUSTOMER_ID_PLEASE_ENTER_A_VALID_USER_ID);
        } else {
            CustomerEntity customer = customerService.findCustomerDetails(customerId);
            if (null == customer) {
                throw new RewardPointsException(CUSTOMER_DOES_NOT_EXISTS_PLEASE_TRY_A_DIFFERENT_CUSTOMER);
            }
        }
       return true;
    }


}
