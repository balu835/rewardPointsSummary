package com.customer.rewardPoints.controller;

import com.customer.rewardPoints.exceptions.RewardPointsException;
import com.customer.rewardPoints.service.business.RewardPointsBusinessService;
import com.customer.rewardPoints.validator.CustomerValidator;
import com.customer.rewardPoints.web.view.RewardPointsView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static com.customer.rewardPoints.validator.CustomerValidator.CUSTOMER_DOES_NOT_EXISTS_PLEASE_TRY_A_DIFFERENT_CUSTOMER;
import static com.customer.rewardPoints.validator.CustomerValidator.RECEIVED_INVALID_CUSTOMER_ID_PLEASE_ENTER_A_VALID_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RewardPointsControllerTest {

    @InjectMocks
    RewardPointsController rewardPointsController;

    @Mock
    CustomerValidator customerValidator;

    @Mock
    RewardPointsBusinessService rewardPointsBusinessService;

    @Test
    public void testFetchRewardWithValidCustomerIDReturnRewardPoints() {
        RewardPointsView rewardPointsViewMock = mock(RewardPointsView.class,RETURNS_DEEP_STUBS);
        when(rewardPointsViewMock.getTotalRewardPoints()).thenReturn(BigDecimal.valueOf(120).setScale(2));
        when(rewardPointsViewMock.getRewardsSummaries().size()).thenReturn(3);
        when(customerValidator.isValidCustomer(1001L)).thenReturn(Boolean.TRUE);
        when(rewardPointsBusinessService.getRewardPointsSummaryView(anyLong())).thenReturn(rewardPointsViewMock);
        ResponseEntity<?> response = rewardPointsController.fetchRewardPoints(1001L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        RewardPointsView rewardPointsView = (RewardPointsView) response.getBody();
        assertNotNull(rewardPointsView);
        assertThat(rewardPointsView.getRewardsSummaries().size()).isEqualTo(3);
        assertThat(rewardPointsView.getTotalRewardPoints()).isEqualTo(BigDecimal.valueOf(120).setScale(2));
    }

    @Test
    public void testFetchRewardWithNullOrZeroCustomerIDReturnBadRequest() {
        when(customerValidator.isValidCustomer(0L)).thenThrow(new RewardPointsException(RECEIVED_INVALID_CUSTOMER_ID_PLEASE_ENTER_A_VALID_USER_ID));
        ResponseEntity<?> response = rewardPointsController.fetchRewardPoints(0L);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(RECEIVED_INVALID_CUSTOMER_ID_PLEASE_ENTER_A_VALID_USER_ID);
    }

    @Test
    public void testFetchRewardWithInvalidCustomerIDReturnBadRequest() {
        when(customerValidator.isValidCustomer(-1L)).thenThrow(new RewardPointsException(RECEIVED_INVALID_CUSTOMER_ID_PLEASE_ENTER_A_VALID_USER_ID));
        ResponseEntity<?> response = rewardPointsController.fetchRewardPoints(-1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(RECEIVED_INVALID_CUSTOMER_ID_PLEASE_ENTER_A_VALID_USER_ID);
    }

    @Test
    public void testFetchRewardWithNonCustomerIDReturnBadRequest() {
        when(customerValidator.isValidCustomer(105L)).thenThrow(new RewardPointsException(CUSTOMER_DOES_NOT_EXISTS_PLEASE_TRY_A_DIFFERENT_CUSTOMER));
        ResponseEntity<?> response = rewardPointsController.fetchRewardPoints(105L);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(CUSTOMER_DOES_NOT_EXISTS_PLEASE_TRY_A_DIFFERENT_CUSTOMER);
    }

}
