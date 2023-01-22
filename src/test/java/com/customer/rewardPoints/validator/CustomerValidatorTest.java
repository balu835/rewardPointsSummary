package com.customer.rewardPoints.validator;

import com.customer.rewardPoints.entity.CustomerEntity;
import com.customer.rewardPoints.exceptions.RewardPointsException;
import com.customer.rewardPoints.service.domain.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.customer.rewardPoints.validator.CustomerValidator.CUSTOMER_DOES_NOT_EXISTS_PLEASE_TRY_A_DIFFERENT_CUSTOMER;
import static com.customer.rewardPoints.validator.CustomerValidator.RECEIVED_INVALID_CUSTOMER_ID_PLEASE_ENTER_A_VALID_USER_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerValidatorTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerValidator customerValidator;

    @Test
    public void testFindCustomerDetailsForInvalidIdThrowsException() {
        assertThatThrownBy(() -> {
            customerValidator.isValidCustomer(-1L);
        }).isInstanceOf(RewardPointsException.class)
                .hasMessage(RECEIVED_INVALID_CUSTOMER_ID_PLEASE_ENTER_A_VALID_USER_ID);
    }

    @Test
    public void testFindCustomerDetailsForIdDoesNotExistsThrowsException(){
        assertThatThrownBy(() -> {
            customerValidator.isValidCustomer(1009L);
        }).isInstanceOf(RewardPointsException.class)
                .hasMessage(CUSTOMER_DOES_NOT_EXISTS_PLEASE_TRY_A_DIFFERENT_CUSTOMER);
    }

    @Test
    public void testFindCustomerDetailsForValidIdReturnsCustomer(){
        when(customerService.findCustomerDetails(1005L)).thenReturn(mock(CustomerEntity.class));
        Boolean isValidCustomer = customerValidator.isValidCustomer(1005L);
        assertTrue(isValidCustomer);
    }
}
