package com.customer.rewardPoints.service.business;

import com.customer.rewardPoints.domain.RewardsSummary;
import com.customer.rewardPoints.entity.OrderEntity;
import com.customer.rewardPoints.service.domain.OrderService;
import com.customer.rewardPoints.web.view.RewardPointsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;

@Service
@Slf4j
public class RewardPointsBusinessService {
    @Autowired
    private OrderService orderService;

    @Value("${order.monthsToSubtract}")
    private Integer monthsToSubtract;

    @Value("${order.oneHundred}")
    private BigDecimal oneHundred;

    @Value("${order.fifty}")
    private BigDecimal fifty;

    @Value("${order.rewards.hundred}")
    private BigDecimal rewardsForHundred;

    /**
     *
     *  To calculate reward points for a given customer Id
     *  A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every
     *  dollar spent over $50 in each transaction
     *  (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
     *
     * @param customerId
     * @return
     */
    public RewardPointsView getRewardPointsSummaryView(Long customerId) {
        log.info("getRewardPointsSummaryView:: getting reward points for customerId {}", customerId);
        RewardPointsView rewardPointsView = new RewardPointsView();
        Date orderDate = Date.from(LocalDate.now().minusMonths(monthsToSubtract)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<OrderEntity> orders = orderService.fetchOrdersForAllPreviousOrdersFromOrderDate(customerId, orderDate);
        Map<Month, BigDecimal> orderSummaryMap = getOrdersSummaryDetails(orders);
        BigDecimal rewardPoints = calculateRewardPointsTotal(orderSummaryMap);
        List<RewardsSummary> rewardsSummaries = populateRewardsSummaries(orderSummaryMap);
        rewardPointsView.setRewardsSummaries(rewardsSummaries);
        rewardPointsView.setTotalRewardPoints(rewardPoints);
        log.info("getRewardPointsSummaryView:: Total reward points for customerId {} are {}", customerId, rewardPoints);
        return  rewardPointsView;
    }

    private List<RewardsSummary> populateRewardsSummaries(Map<Month, BigDecimal> orderSummaryMap) {
        List<RewardsSummary> rewardsSummaries = new ArrayList<>();
        for(Month month : orderSummaryMap.keySet()) {
            RewardsSummary rewardsSummary = new RewardsSummary();
            BigDecimal monthlyTotalAmount = orderSummaryMap.get(month);
            rewardsSummary.setTotalMonthlyAmount(monthlyTotalAmount);
            rewardsSummary.setOrderMonth(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            rewardsSummary.setTotalMonthlyRewardPoints(calculateRewardPoints(monthlyTotalAmount));
            rewardsSummaries.add(rewardsSummary);
        }
        return  rewardsSummaries;
    }

    private BigDecimal calculateRewardPointsTotal( Map<Month, BigDecimal> orderSummaryMap) {
        BigDecimal totalRewardPoints = BigDecimal.ZERO.setScale(2);
        for (BigDecimal monthlyTotal : orderSummaryMap.values()){
            totalRewardPoints = totalRewardPoints.add(calculateRewardPoints(monthlyTotal));
        }
        return totalRewardPoints;
    }
    private static Map<Month, BigDecimal> getOrdersSummaryDetails(List<OrderEntity> orders) {
        Map<Month, BigDecimal> orderSummaryMap = new HashMap<>();
        for(OrderEntity order : orders){
            LocalDate orderDate = order.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Month month =  orderDate.getMonth();
            if(!orderSummaryMap.containsKey(month)) {
                orderSummaryMap.put(month, order.getOrderAmount());
            } else {
                orderSummaryMap.put(month, orderSummaryMap.get(month).add(order.getOrderAmount()));
            }
        }
        return orderSummaryMap;
    }

    private BigDecimal calculateRewardPoints(BigDecimal totalOrderAmount) {
        log.info("calculateRewardPoints:: Started Calculating Rewards points for the totalOrderAmount {}", totalOrderAmount);
        BigDecimal totalRewardPoints = BigDecimal.ZERO.setScale(2);
        if(null != totalOrderAmount){
            if(totalOrderAmount.compareTo(oneHundred) > 0){
                totalRewardPoints = fifty.add(totalOrderAmount.subtract(oneHundred).multiply(rewardsForHundred));
            } else if (totalOrderAmount.compareTo(fifty) > 0) {
                totalRewardPoints = totalOrderAmount.subtract(fifty);
            }
        }
        log.info("calculateRewardPoints:: Total reward points calculated  for the totalOrderAmount {} with totalRewardPoints {}", totalOrderAmount, totalRewardPoints);
        return  totalRewardPoints.setScale(2, RoundingMode.HALF_UP);
    }

}
