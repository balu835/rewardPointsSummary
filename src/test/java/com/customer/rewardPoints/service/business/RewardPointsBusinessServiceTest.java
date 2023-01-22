package com.customer.rewardPoints.service.business;

import com.customer.rewardPoints.entity.CustomerEntity;
import com.customer.rewardPoints.entity.OrderEntity;
import com.customer.rewardPoints.exceptions.RewardPointsException;
import com.customer.rewardPoints.service.domain.OrderService;
import com.customer.rewardPoints.web.view.RewardPointsView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RewardPointsBusinessServiceTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private RewardPointsBusinessService rewardPointsBusinessService;

    private Date orderDate;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(rewardPointsBusinessService, "monthsToSubtract", 3);
        ReflectionTestUtils.setField(rewardPointsBusinessService, "oneHundred", BigDecimal.valueOf(100));
        ReflectionTestUtils.setField(rewardPointsBusinessService, "fifty", BigDecimal.valueOf(50));
        ReflectionTestUtils.setField(rewardPointsBusinessService, "rewardsForHundred", BigDecimal.valueOf(2));
        orderDate = getOrderDate(3);
    }

    @Test
    public void testGetRewardPointsSummaryViewWithZeroCustomerIdReturnsZeroPoints() {
        when(orderService.fetchOrdersForAllPreviousOrdersFromOrderDate(0L,orderDate)).thenReturn(Collections.emptyList());
        RewardPointsView view = rewardPointsBusinessService.getRewardPointsSummaryView(0L);
        assertNotNull(view);
        assertThat(view.getTotalRewardPoints()).isEqualTo(BigDecimal.ZERO.setScale(2));
    }
    @Test
    public void testGetRewardPointsSummaryViewWithCustomerIdNegativeValueThrowsException() {
        when(orderService.fetchOrdersForAllPreviousOrdersFromOrderDate(-1L,orderDate)).thenReturn(Collections.emptyList());
        RewardPointsView view = rewardPointsBusinessService.getRewardPointsSummaryView(-1L);
        assertNotNull(view);
        assertThat(view.getTotalRewardPoints()).isEqualTo(BigDecimal.ZERO.setScale(2));
    }
    @Test
    public void testGetRewardPointsSummaryViewWithValidCustomerNoOrdersReturnsZeroPoints() {
        when(orderService.fetchOrdersForAllPreviousOrdersFromOrderDate(anyLong(),any())).thenReturn(Collections.emptyList());
        RewardPointsView view = rewardPointsBusinessService.getRewardPointsSummaryView(1001L);
        assertNotNull(view);
        assertThat(view.getTotalRewardPoints()).isEqualTo(BigDecimal.ZERO.setScale(2));
    }

    @Test
    public void testGetRewardPointsSummaryViewWithValidDataReturnsPoints() {
        List<OrderEntity> validOutdatedOrders = getValidOrdersData();
        when(orderService.fetchOrdersForAllPreviousOrdersFromOrderDate(1001L,orderDate)).thenReturn(validOutdatedOrders);
        RewardPointsView view = rewardPointsBusinessService.getRewardPointsSummaryView(1001L);
        assertNotNull(view);
        assertThat(view.getTotalRewardPoints()).isEqualTo(BigDecimal.valueOf(273.36).setScale(2));
    }

    @Test
    public void testGetRewardPointsSummaryViewWithValidCustomerWithOldOrdersReturnsZeroPoints() {
        List<OrderEntity> oldOrders = getOldDatedOrdersData();
        when(orderService.fetchOrdersForAllPreviousOrdersFromOrderDate(1001L,orderDate)).thenReturn(oldOrders);
        RewardPointsView view = rewardPointsBusinessService.getRewardPointsSummaryView(1001L);
        assertNotNull(view);
        assertThat(view.getTotalRewardPoints()).isEqualTo(BigDecimal.ZERO.setScale(2));
    }

    @Test
    public void testGetRewardPointsSummaryViewWithValidOldAndCurrentOrdersReturnsPoints() {
        List<OrderEntity> validOutdatedOrders = getValidAndOldDatedOrdersData();
        when(orderService.fetchOrdersForAllPreviousOrdersFromOrderDate(1001L,orderDate)).thenReturn(validOutdatedOrders);
        RewardPointsView view = rewardPointsBusinessService.getRewardPointsSummaryView(1001L);
        assertNotNull(view);
        assertThat(view.getTotalRewardPoints()).isEqualTo(BigDecimal.valueOf(40).setScale(2));
    }

    private static List<OrderEntity> getValidOrdersData() {
        List<OrderEntity> validOrders = new ArrayList<>();
        Date orderDate = Date.from(LocalDate.now().minusMonths(1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        CustomerEntity customer = mock(CustomerEntity.class, RETURNS_DEEP_STUBS);
        OrderEntity order1 = mock(OrderEntity.class, RETURNS_DEEP_STUBS);
        when(order1.getCustomer()).thenReturn(customer);
        when(order1.getOrderAmount()).thenReturn(BigDecimal.valueOf(90).setScale(2));
        when(order1.getOrderDate()).thenReturn(orderDate);
        OrderEntity order2 = mock(OrderEntity.class, RETURNS_DEEP_STUBS);
        when(order2.getCustomer()).thenReturn(customer);
        when(order2.getOrderAmount()).thenReturn(BigDecimal.valueOf(121.68).setScale(2));
        when(order2.getOrderDate()).thenReturn(orderDate);
        OrderEntity order3 = mock(OrderEntity.class, RETURNS_DEEP_STUBS);
        when(order3.getCustomer()).thenReturn(customer);
        when(order3.getOrderAmount()).thenReturn(BigDecimal.valueOf(244.68).setScale(2));
        when(order3.getOrderDate()).thenReturn(orderDate);
        validOrders.add(order1);
        validOrders.add(order2);
        return validOrders;
    }

    private static List<OrderEntity> getValidAndOldDatedOrdersData() {
        List<OrderEntity> validOrders = new ArrayList<>();
        Date orderDate = getOrderDate(1);
        Date oldOrderDate = getOrderDate(4);
        CustomerEntity customer = mock(CustomerEntity.class, RETURNS_DEEP_STUBS);
        OrderEntity order1 = mock(OrderEntity.class, RETURNS_DEEP_STUBS);
        when(order1.getCustomer()).thenReturn(customer);
        when(order1.getOrderAmount()).thenReturn(BigDecimal.valueOf(90).setScale(2));
        when(order1.getOrderDate()).thenReturn(orderDate);
        OrderEntity order2 = mock(OrderEntity.class, RETURNS_DEEP_STUBS);
        when(order2.getCustomer()).thenReturn(customer);
        when(order2.getOrderAmount()).thenReturn(BigDecimal.valueOf(30).setScale(2));
        when(order2.getOrderDate()).thenReturn(oldOrderDate);
        validOrders.add(order1);
        validOrders.add(order2);
        return validOrders;
    }

    private static List<OrderEntity> getOldDatedOrdersData() {
        List<OrderEntity> validOrders = new ArrayList<>();
        Date orderDate = getOrderDate(5);
        Date oldOrderDate = getOrderDate(5);
        CustomerEntity customer = mock(CustomerEntity.class, RETURNS_DEEP_STUBS);
        OrderEntity order1 = mock(OrderEntity.class, RETURNS_DEEP_STUBS);
        when(order1.getCustomer()).thenReturn(customer);
        when(order1.getOrderAmount()).thenReturn(BigDecimal.valueOf(10.23).setScale(2));
        when(order1.getOrderDate()).thenReturn(orderDate);
        OrderEntity order2 = mock(OrderEntity.class, RETURNS_DEEP_STUBS);
        when(order2.getCustomer()).thenReturn(customer);
        when(order2.getOrderAmount()).thenReturn(BigDecimal.valueOf(30.35).setScale(2));
        when(order2.getOrderDate()).thenReturn(oldOrderDate);
        validOrders.add(order1);
        validOrders.add(order2);
        return validOrders;
    }
    private static Date getOrderDate(Integer month) {
        return Date.from(LocalDate.now().minusMonths(month)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
