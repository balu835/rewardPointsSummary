package com.customer.rewardPoints.service.domain;

import com.customer.rewardPoints.entity.CustomerEntity;
import com.customer.rewardPoints.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testFindCustomerDetailsForInvalidIdReturnsNull(){
        when(customerRepository.findByCustomerId(-1L)).thenReturn(null);
        CustomerEntity customer = customerService.findCustomerDetails(-1L);
        assertNull(customer);
    }

    @Test
    public void testFindCustomerDetailsForValidIdReturnsCustomer(){
        CustomerEntity mockCustomer = mock(CustomerEntity.class);
        when(customerRepository.findByCustomerId(1005L)).thenReturn(mockCustomer);
        CustomerEntity customer = customerService.findCustomerDetails(1005L);
        assertNotNull(customer);
    }
}
